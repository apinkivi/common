package fi.apinkivi.common

import kotlinx.serialization.Serializable

/**
 * [Semantic version](https://semver.org/)
 *
 * Given a version number [major].[minor].[patch], increment:
 *
 * - The [major] version when you make incompatible API changes
 * - The [patch] version when you add functionality in a backward compatible manner
 * - The [patch] version when you make backward compatible bug fixes
 *
 * Additional labels for pre-release and build metadata are available as extensions to the [major].[minor].[patch]
 * format.
 */
@JvmInline
@Serializable
value class Version(
    val value: String = "0.1.0",
) {
    /** Match result */
    private val result get() = regex.matchEntire(value)

    /** Basic version */
    val core get() =
        result?.run {
            groupValues[MAJOR_GROUP] + "." + groupValues[MINOR_GROUP] + "." + groupValues[PATCH_GROUP]
        } ?: value.strBeforeChained('-', '+')

    /**
     * Major version X (X.y.z | X > 0) **must** be incremented if any backward incompatible changes are introduced to
     * the public API. It **may** also include [minor] and [patch] level changes. [patch] and [minor] versions **must**
     * be reset to 0 when a major version is incremented.
     */
    val major get() = result?.groupValues[MAJOR_GROUP]?.toInt() ?: value.intBefore('.')

    /**
     * Minor version Y (x.Y.z | x > 0) **must** be incremented if new, backward-compatible functionality is introduced
     * to the public API. It **must** be incremented if any public API functionality is marked as deprecated. It **may**
     * be incremented if significant new functionality or improvements are introduced within the private code. It
     * **may** include patch level changes. Patch version **must** be reset to 0 when the minor version is incremented.
     */
    val minor get() = result?.groupValues[MINOR_GROUP]?.toInt() ?: value.intBetweenFirst('.', '.')

    /**
     * Patch version Z (x.y.Z | x > 0) **must** be incremented if only backward compatible bug fixes are introduced. A
     * bug fix is defined as an internal change that fixes incorrect behavior.
     */
    val patch get() = result?.groupValues[PATCH_GROUP]?.toInt() ?: value.intAfterChained('.', '.')

    /**
     * A pre-release version **may** be denoted by appending a hyphen and a series of dot-separated identifiers
     * immediately following the patch version. Identifiers **must** comprise only ASCII alphanumerics and hyphens
     * [0-9A-Za-z-]. Identifiers **mustn't** be empty. Numeric identifiers **mustn't** include leading zeroes.
     * Pre-release versions have a lower precedence than the associated normal version. A pre-release version indicates
     * that the version is unstable and might not satisfy the intended compatibility requirements as denoted by its
     * associated normal version.
     *
     * Examples: 1.0.0-alpha, 1.0.0-alpha.1, 1.0.0-0.3.7, 1.0.0-x.7.z.92, 1.0.0-x-y-z.--.
     */
    val preRelease get() = result?.groupValues[PRE_RELEASE_GROUP] ?: value.strBetweenFirst('-', '+')

    /**
     * Build metadata **may** be denoted by appending a plus sign and a series of dot-separated identifiers immediately
     * following the patch or pre-release version. Identifiers **must** comprise only ASCII alphanumerics and hyphens
     * [0-9A-Za-z-]. Identifiers **mustn't** be empty. Build metadata **must** be ignored when determining version
     * precedence. Thus, two versions that differ only in the build metadata have the same precedence.
     *
     * Examples: 1.0.0-alpha+001, 1.0.0+20130313144700, 1.0.0-beta+exp.sha.5114f85, 1.0.0+21AF26D3----117B344092BD.
     */
    val build get() = result?.groupValues[BUILD_GROUP] ?: value.substringAfter('+', "")

    /**
     * A normal version number **must** take the form X.Y.Z where X, Y, and Z are non-negative integers, and **mustn't**
     * contain leading zeroes. X is the major version, Y is the minor version, and Z is the patch version. Each element
     * **must** increase numerically. For instance: 1.9.0 -> 1.10.0 -> 1.11.0.
     */
    val normal get(): Boolean {
        val groupValues = result?.groupValues
        if (groupValues != null) {
            return groupValues[PRE_RELEASE_GROUP].isBlank() && groupValues[BUILD_GROUP].isBlank()
        } else {
            val (major, minor, patch) = this
            return major >= 0 && minor >= 0 && patch >= 0 && "$major.$minor.$patch" == value
        }
    }

    /**
     * Major version zero (0.y.z) is for initial development. Anything **may** change at any time. The public API
     * **shouldn't** be considered stable.
     */
    val stable get() = major > 0 && preRelease.isEmpty()

    val valid get() = regex.matches(value)

    operator fun component1() = major

    operator fun component2() = minor

    operator fun component3() = patch

    operator fun component4() = preRelease

    operator fun component5() = build

    override fun toString() = value

    companion object {
        const val MAJOR_GROUP = 1
        const val MINOR_GROUP = 2
        const val PATCH_GROUP = 3
        const val PRE_RELEASE_GROUP = 4
        const val BUILD_GROUP = 5
        val regex =
            Regex(
                """^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)""" +
                    """(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?$""",
            )
    }
}
