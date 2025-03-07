package com.panneikhin.md_assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.panneikhin.md_assignment.ui.theme.MD_AssignmentTheme
import org.json.JSONArray
import org.json.JSONException

class MainActivity : ComponentActivity() {

    lateinit var name:String
    lateinit var password:String
    private val URL = "http://10.0.2.2/mobileapp/login.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        val img:ImageView = findViewById(R.id.loginimageview)
        val height = resources.displayMetrics.heightPixels
        img.layoutParams.height = height/2
        img.requestLayout()

        val tvToRegister: TextView = findViewById(R.id.textViewtoRegister)
        tvToRegister.setOnClickListener()
        {
           var intent = Intent(this,RegisterAct::class.java)
            startActivity(intent)

        }
        val btnLogin: Button = findViewById(R.id.buttonLogin)
        btnLogin.setOnClickListener(){
            name = findViewById<EditText>(R.id.editTextLoginEmail).text.toString()
            password = findViewById<EditText>(R.id.editTextLoginPass).text.toString()
            login()

        }

        val btnloc:FloatingActionButton=findViewById(R.id.locBtn)
        btnloc.setOnClickListener()
        {
            var i= Intent(this, MapsActivity::class.java)
            startActivity(i)
        }
}

    fun login()
    {
        var request = object: StringRequest(
            Method.POST, URL,
            { respond ->
                try {
                    var arr = JSONArray(respond)
                    var obj = arr.getJSONObject(0)
                    var uid: Int = obj.getInt("uid")
                    if (uid == -1)
                        Toast.makeText(this, "Try Again!", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this, "Login Successful!"+uid, Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "JSON Parsing error: ${e.message}", Toast.LENGTH_LONG).show()

                }
            },
            { error ->
                Toast.makeText(this, "error"+error.message, Toast.LENGTH_LONG).show()
            }) {

            override fun getParams(): MutableMap<String, String>? {
                var para = HashMap<String, String>()
                para.put("uname", name)
                para.put("upassword", password)
                return para
            }
        }
        Volley.newRequestQueue(this).add(request)
    }


}

