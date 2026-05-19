package fi.apinkivi.common

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VersionTest {
    @Test
    fun testBasicVersion() {
        val version = Version("1.2.3")
        assertEquals(1, version.major)
        assertEquals(2, version.minor)
        assertEquals(3, version.patch)
        assertEquals("", version.preRelease)
        assertEquals("", version.build)
        assertEquals("1.2.3", version.core)
        assertTrue(version.normal)
        assertTrue(version.stable)
        assertTrue(version.valid)
    }

    @Test
    fun testPreRelease() {
        val version = Version("1.2.3-alpha.1")
        assertEquals(1, version.major)
        assertEquals(2, version.minor)
        assertEquals(3, version.patch)
        assertEquals("alpha.1", version.preRelease)
        assertEquals("", version.build)
        assertEquals("1.2.3", version.core)
        assertFalse(version.normal)
        assertFalse(version.stable)
        assertTrue(version.valid)
    }

    @Test
    fun testBuildMetadata() {
        val version = Version("1.2.3+build.123")
        assertEquals(1, version.major)
        assertEquals(2, version.minor)
        assertEquals(3, version.patch)
        assertEquals("", version.preRelease)
        assertEquals("build.123", version.build)
        assertEquals("1.2.3", version.core)
        assertFalse(version.normal)
        assertTrue(version.stable)
        assertTrue(version.valid)
    }

    @Test
    fun testPreReleaseAndBuild() {
        val version = Version("1.2.3-beta.2+exp.sha.5114f85")
        assertEquals(1, version.major)
        assertEquals(2, version.minor)
        assertEquals(3, version.patch)
        assertEquals("beta.2", version.preRelease)
        assertEquals("exp.sha.5114f85", version.build)
        assertEquals("1.2.3", version.core)
        assertFalse(version.normal)
        assertFalse(version.stable)
        assertTrue(version.valid)
    }

    @Test
    fun testUnstable() {
        val version = Version("0.1.0")
        assertEquals(0, version.major)
        assertEquals(1, version.minor)
        assertEquals(0, version.patch)
        assertFalse(version.stable)
        assertTrue(version.valid)
    }

    @Test
    fun testDestructuring() {
        @Suppress("detekt:DestructuringDeclarationWithTooManyEntries")
            val (major, minor, patch, pre, build) = Version("1.2.3-alpha+build")
        assertEquals(1, major)
        assertEquals(2, minor)
        assertEquals(3, patch)
        assertEquals("alpha", pre)
        assertEquals("build", build)
    }

    @Test
    fun testValidAndInvalid() {
        assertTrue(Version("1.0.0").valid)
        assertTrue(Version("1.0.0-alpha").valid)
        assertTrue(Version("1.0.0-alpha.1").valid)
        assertTrue(Version("1.0.0-0.3.7").valid)
        assertTrue(Version("1.0.0-x.7.z.92").valid)
        assertTrue(Version("1.0.0+20130313144700").valid)
        assertTrue(Version("1.0.0-beta+exp.sha.5114f85").valid)

        assertFalse(Version("1.0").valid)
        assertFalse(Version("1.0.01").valid)
        assertFalse(Version("1.0.0-alpha..1").valid)
        assertFalse(Version("v1.0.0").valid)
    }

    @Test
    fun testNormal() {
        assertTrue(Version("1.2.3").normal)
        assertFalse(Version("1.2.3-alpha").normal)
        assertFalse(Version("1.2.3+build").normal)
        // normal requires that "$major.$minor.$patch" == value
        // So "01.2.3" is abnormal if major returns 1
        assertFalse(Version("01.2.3").normal)
    }

    @Test
    fun testInvalidInput() {
        // Test how Version behaves if input is completely wrong
        // Current implementation throws NumberFormatException if matchResult is null and Strings functions fail
        try {
            val v = Version("invalid")
            v.major
        } catch (e: Exception) {
            assertTrue(e is NumberFormatException || e is NoSuchElementException)
        }
    }
}
