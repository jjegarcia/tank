package exercises

class Invoker( val myInterface: MyInterface) {
    fun functionInvoke(): String {
        myInterface.open()
        return "b"
    }

}
