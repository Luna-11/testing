package com.panneikhin.md_assignment

import android.content.ContextParams
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class RegisterAct : AppCompatActivity() {
    lateinit var name:String
    lateinit var password:String
    lateinit var confirmpassword:String
    lateinit var weight:String
    private val URL ="http://10.0.2.2/mobileapp/registernew.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.register)
        val btn:Button = findViewById<Button>(R.id.btnRegister)
        btn.setOnClickListener()
        {
            name=findViewById<EditText>(R.id.editTextRegMail).text.toString()
            password=findViewById<EditText>(R.id.editTextRegPass).text.toString()
            confirmpassword=findViewById<EditText>(R.id.editTextRegConfirmPass).text.toString()
            weight = findViewById<EditText>(R.id.editTextweight).text.toString()
            register()
        }
    }
    fun register()
    {

        var stringReq= object :StringRequest(Request.Method.POST,URL,
            {
                respond->
                    var arr=JSONArray(respond)
                    var obj =arr.getJSONObject(0)
                    var uid= obj.getInt("uid")
                    if(uid<1)
                    {
                        Toast.makeText(this,"error"+ uid, Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this,"Success"+ uid, Toast.LENGTH_LONG).show()
                    }
            } ,{
                error->
                Log.d("NetworkUtility", "Sending request to: " + URL);

                Toast.makeText(this, error.message.toString(),Toast.LENGTH_LONG).show()
            }
            )
        {
            override fun getParams(): MutableMap<String, String>? {
                var para = HashMap<String,String>()
                para.put("uname",name)
                para.put("upassword",password)
                para.put("uconfirmpassword",confirmpassword)
                para.put("uweight",weight)

                return para
            }
        }
        Volley.newRequestQueue(this).add(stringReq)
    }
}