import scala.language.adhocExtensions
import org.scalatest.funsuite.AnyFunSuite

class testDummy extends AnyFunSuite:
  test("Dummy test"):
    val input = "7"
    val expected = 7
    assert(Dummy.stringToInt(input) === expected)
