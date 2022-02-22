package com.example.basic_calculator

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.basic_calculator.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val strError : String = "Error"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.nine.tag = 9
        binding.eight.tag = 8
        binding.seven.tag = 7
        binding.six.tag = 6
        binding.five.tag = 5
        binding.four.tag = 4
        binding.tree.tag = 3
        binding.two.tag = 2
        binding.one.tag = 1
        binding.zero.tag = 0
        binding.plus.tag = 11
        binding.minus.tag = 12
        binding.multiplication.tag = 13
        binding.division.tag = 14
        binding.squarexy.tag = 15
        binding.leftparantesis.tag = 16
        binding.rightparantesis.tag = 17

    }
    fun writeToTxtBox(view: View){
        var txt : String = ""
        when(view.getTag()) {
            9 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            8 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            7 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            6 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            5 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            4 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            3 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            2 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            1 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            0 -> {
                txt = binding.textofnumbers.text.toString() + view.getTag().toString()
                binding.textofnumbers.text = txt
            }
            11 -> {
                txt = binding.textofnumbers.text.toString() + "+"
                binding.textofnumbers.text = txt
            }
            12 -> {
                txt = binding.textofnumbers.text.toString() + "-"
                binding.textofnumbers.text = txt
            }
            13 -> {
                txt = binding.textofnumbers.text.toString() + "*"
                binding.textofnumbers.text = txt
            }
            14 -> {
                txt = binding.textofnumbers.text.toString() + "/"
                binding.textofnumbers.text = txt
            }
            15 -> {
                txt = binding.textofnumbers.text.toString()
                binding.textofnumbers.text = txt
            }
            16 -> {
                txt = binding.textofnumbers.text.toString() + "("
                binding.textofnumbers.text = txt
            }
            17 -> {
                txt = binding.textofnumbers.text.toString() + ")"
                binding.textofnumbers.text = txt
            }

        }
    }
    fun calculateresult(view: View){
        try {
            val shuntC = shuntingYardCalculator(binding.textofnumbers.text.toString())
            shuntC.listConverter()
            shuntC.precedenceGenerator()
            shuntC.numberQueue
            binding.textofnumbers.text =  "${binding.textofnumbers.text } = ${shuntC.calculator()}"
        }
        catch (e : Exception){
            binding.textofnumbers.text = strError
        }


    }
    fun deletetxt(view: View){
        binding.textofnumbers.text = ""
    }
    fun deletetxtOnebyOne(view: View){
        binding.textofnumbers.text = binding.textofnumbers.text.dropLast(1)
    }
    fun makeFun (view: View){
        val buttonList : ArrayList<Any> = arrayListOf(binding.zero,binding.one,binding.two,binding.tree,binding.four,
            binding.five,binding.six,binding.seven,binding.eight,binding.nine)
        var v2 : View
        for(it in 0..17){
            Handler(Looper.getMainLooper()).postDelayed({
                v2 = buttonList[(0..9).random()] as View
                v2.setBackgroundColor(Color.rgb((0..255).random(),(0..255).random(),(0..255).random()))
            }, 40)
        }

    }
}

class shuntingYardCalculator(val equation: String){

    val precedence : HashMap<Char,Int> = hashMapOf(('(' to 0),(')' to 1),('*' to 2),('/' to 2),('+' to 3),('-' to 3))
    var operatorStack  = ArrayDeque<Any>()
    var numberQueue : Queue<Any> = LinkedList<Any>()
    var input :MutableList<Any> = mutableListOf()

    // Converts the the string equation to list
    fun listConverter(){
        //Temporary list to hold that digits have one or more digits
        var tmplist : ArrayList<Float> = arrayListOf()
        //Temporary integer to hold result of the one or more digit number
        var tmpint : Float = 0f
        //Powcounter to evaluate digit correctly
        var powcounter: Int = 0
        //İterates through the string equation
        for (i in equation.indices){
            //checks the number or symbol
            if(equation[i].isDigit()){
                //Adds the digit to list
                tmplist.add(equation[i].digitToInt().toFloat())
                //Checks the digit and after element to verify the how many digit it has
                if(!((i < equation.length-1) && equation[i+1].isDigit())){
                    for(x in tmplist.size-1 downTo 0){
                        tmpint += tmplist[x]*(Math.pow(10.toDouble(),powcounter.toDouble())).toFloat()
                        powcounter++
                    }
                    //Adds to the input list
                    input += tmpint
                    //Clears the temporary list
                    tmplist.clear()
                    powcounter = 0
                }
            }
            //Specify the symbol and adds to the list
            else {
                input += equation[i]
                tmplist.clear()
                tmpint = 0f
            }
        }
    }
    // Generates the RPN as Queue
    fun precedenceGenerator(){
        // Variable to hold indexes
        var inputIndex : Int = 0
        // Variable to hold stackoperator precedence
        var precedenceHolderstack: Int = 0
        // Variable to hold coming operator precedence
        var precedenceHolderinput: Int = 0
        // Variable to hol popped value
        var tmp : Any
        // Loop that stops when the input list is empty
        while(input.isNotEmpty()){
            // Checks the value of the input whether char or not
            if(input[inputIndex] is Char){
                // Checks the operatorstack is emty or not
                if(operatorStack.isEmpty()){
                    // if operatorstack is empty pushes the first operator to the stack
                    operatorStack.push(input[inputIndex])
                    // Removes the pushed varialbe from input list
                    input.removeAt(inputIndex)
                }
                // input variable is not char, it goes to this scope
                else{
                    // Hold operatorstack and input precedences
                    precedenceHolderstack = precedence.getValue(operatorStack.peek() as Char)
                    precedenceHolderinput = precedence.getValue(input[inputIndex] as Char)
                    // Condition controls
                    if ((precedenceHolderstack <= precedenceHolderinput) && precedenceHolderstack != 0 && precedenceHolderstack != 1){
                        tmp = operatorStack.pop()
                        numberQueue.add(tmp as Char)
                        operatorStack.push(input[inputIndex])
                        input.removeAt(inputIndex)
                    }else if ((precedenceHolderstack <= precedenceHolderinput) && (precedenceHolderstack == 0 || precedenceHolderstack == 1)){
                        operatorStack.push(input[inputIndex])
                        input.removeAt(inputIndex)
                    }else if(precedenceHolderinput == 0){
                        operatorStack.push(input[inputIndex])
                        input.removeAt(inputIndex)
                    }else if(precedenceHolderinput == 1){
                        while (operatorStack.peek() != '('){
                            numberQueue.add(operatorStack.pop())
                        }
                        operatorStack.pop()
                        input.removeAt(inputIndex)
                    }else if(precedenceHolderstack > precedenceHolderinput){
                        operatorStack.push(input[inputIndex])
                        input.removeAt(inputIndex)
                    }
                }
            }else{
                numberQueue.add(input[inputIndex])
                input.removeAt(inputIndex)
            }
            // If the processes end, this will drain down the operator stack to the queue
            if (input.size == 0 && operatorStack.isNotEmpty()){
                while(operatorStack.isNotEmpty()) {
                    numberQueue.add(operatorStack.pop())
                }
            }
        }
    }
    // Evaluates the RPN
    fun calculator() : String{
        // List to hold RPN
        var listRPN: MutableList<Any> = mutableListOf()
        // Variable to count index of RPN
        var indexCounter : Int = 0
        // Variable to hold results
        var tempint : Float = 0F
        // Transfers queue values to list
        while (numberQueue.isNotEmpty()){
            listRPN.add(numberQueue.remove())
        }
        // Works until RPN size equals to 1
        while (listRPN.size != 1){
            if(listRPN[indexCounter] is Char){
                if (listRPN[indexCounter] == '*'){
                    tempint = listRPN[indexCounter-2].toString().toFloat() * listRPN[indexCounter-1].toString().toFloat()
                    listRPN[indexCounter] = tempint
                    listRPN.removeAt(indexCounter-1)
                    listRPN.removeAt(indexCounter-2)
                }else if (listRPN[indexCounter] == '/'){
                    tempint = listRPN[indexCounter-2].toString().toFloat() / listRPN[indexCounter-1].toString().toFloat()
                    listRPN[indexCounter] = tempint
                    listRPN.removeAt(indexCounter-1)
                    listRPN.removeAt(indexCounter-2)
                }else if (listRPN[indexCounter] == '+'){
                    tempint = listRPN[indexCounter-2].toString().toFloat() + listRPN[indexCounter-1].toString().toFloat()
                    listRPN[indexCounter] = tempint
                    listRPN.removeAt(indexCounter-1)
                    listRPN.removeAt(indexCounter-2)
                }else if (listRPN[indexCounter] == '-'){
                    tempint = listRPN[indexCounter-2].toString().toFloat() - listRPN[indexCounter-1].toString().toFloat()
                    listRPN[indexCounter] = tempint
                    listRPN.removeAt(indexCounter-1)
                    listRPN.removeAt(indexCounter-2)
                }
                indexCounter -= 2
            }
            indexCounter++
        }
        return listRPN[0].toString()
    }
}