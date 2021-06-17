package exercises

import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test


class Excercise1 {

    @Test
    fun `if first Letter in range return true`() {

        val verifyString: String = "Bla"
        val verifier = Verifier()
        assertEquals(verifier.isFirstCharacterLetter(verifyString), true)
    }

    @Test
    fun `if first Letter not in range return false`() {

        val verifyString: String = "1la"
        val verifier = Verifier()
        assertEquals(verifier.isFirstCharacterLetter(verifyString), false)
    }

}

class Verifier {
    val validRangeList: List<Char> = getValidList()

    private fun getValidList(): List<Char> {
        val response = mutableListOf<Char>()
        var nextChar = 'A'
        while (nextChar < 'Z') {
            response.add(nextChar)
            nextChar++
        }
        return response.toList()
    }

    fun isFirstCharacterLetter(verifyString: String): Boolean {
        return isAZ(verifyString[0])
    }

    private fun isAZ(c: Char): Boolean {
        return validRangeList.contains(c)
    }
}
