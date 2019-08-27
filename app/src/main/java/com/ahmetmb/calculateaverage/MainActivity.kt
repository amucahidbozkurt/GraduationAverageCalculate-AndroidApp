package com.ahmetmb.calculateaverage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_new_class.view.*

class MainActivity : AppCompatActivity() {

    private val classesName = arrayOf("Mathematics", "Physics", "Internet Programming", "Database Systems")
    private val allClassesInfo: ArrayList<Classes> = ArrayList(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ders adi oneri sunmak icin adapter kullandik
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, classesName)
        textAddClass.setAdapter(adapter)

        //CALCULATE BUTTON
        if (rootLayout.childCount == 0) {
            buttonCalculate.visibility = View.INVISIBLE
        } else buttonCalculate.visibility = View.VISIBLE

        //ADD BUTTON
        buttonAddClass.setOnClickListener {

            if (!textAddClass.text.isNullOrEmpty()) {

                var inflater = LayoutInflater.from(this)
                var newClassView = inflater.inflate(R.layout.add_new_class, null) //view olarak donderiyor

                newClassView.textNewAddClass.setAdapter(adapter)

                //static alandan kullanicinin girdigi degerleri aliyoruz
                var className = textAddClass.text.toString()
                var classCredit = spinnerClassCredits.selectedItem.toString()
                var classLetterGrade = spinnerLetterGrade.selectedItem.toString()

                //add_new_class'imiz da degerlerin yerini belli ediyoruz
                newClassView.textNewAddClass.setText(className)
                newClassView.spinnerNewClassCredits.setSelection(spinnerSearchIndexValue(spinnerClassCredits, classCredit)) //position icin index fun yazacagiz
                newClassView.spinnerNewLetterGrade.setSelection(spinnerSearchIndexValue(spinnerLetterGrade, classLetterGrade))

                //DELETE BUTTON
                newClassView.buttonDeleteClass.setOnClickListener {
                    rootLayout.removeView(newClassView)

                    //butonu kontrol ediyoruz
                    if (rootLayout.childCount == 0) {
                        buttonCalculate.visibility = View.INVISIBLE
                    } else buttonCalculate.visibility = View.VISIBLE
                }

                rootLayout.addView(newClassView) //scrool icine view olarak ekledik
                buttonCalculate.visibility = View.VISIBLE //butonu kontrol ediyoruz

                cleanTextsArea()

            } else {
                FancyToast.makeText(this,"Write Class Name!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show()
            }

        }

    }

    private fun cleanTextsArea(){
        textAddClass.setText("")
        spinnerClassCredits.setSelection(0)
        spinnerLetterGrade.setSelection(0)
    }

    private fun spinnerSearchIndexValue(spinner: Spinner, searchValue: String): Int {

        var index = 0
        for (i in 0..spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(searchValue)){
                index = i
                break
            }

        }
        return index
    }

    fun calculateAverage(view: View) {

        var totalNote = 0.0
        var totalCredit = 0.0

        for (i in 0 until rootLayout.childCount) {

            var rootValue = rootLayout.getChildAt(i)

            var temporalClass = Classes(rootValue.textNewAddClass.text.toString(),
                ((rootValue.spinnerNewClassCredits.selectedItemPosition )+1).toString(),
                rootValue.spinnerNewLetterGrade.selectedItem.toString())

            allClassesInfo.add(temporalClass)
        }

        for (nowClass in allClassesInfo) {

            totalNote += letterGradeToDoubleValue(nowClass.classLetterGrade) * (nowClass.classCredit.toDouble())
            totalCredit += nowClass.classCredit.toDouble()
        }

        FancyToast.makeText(this,"AVERAGE: " + (totalNote / totalCredit).formatShort(2), FancyToast.LENGTH_LONG,FancyToast.INFO,false).show()
        allClassesInfo.clear()
    }

    // Gelen harf notunu Double degere ceviriyoruz
    fun letterGradeToDoubleValue (letterGradeValue: String): Double {

        var value = 0.0

        when(letterGradeValue) {

            "AA" -> value = 4.0
            "BA" -> value = 3.5
            "BB" -> value = 3.0
            "CB" -> value = 2.5
            "CC" -> value = 2.0
            "DC" -> value = 1.5
            "DD" -> value = 1.0
            "FF" -> value = 0.0

        }

        return value
    }

    // Ekrana basilan Double degeri 0.00 formatina getirdik
    fun Double.formatShort(showNumber: Int) = java.lang.String.format("%.${showNumber}f", this)

}
