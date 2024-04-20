package com.myPackages.searchtrains

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myPackages.searchtrains.databinding.ActivitySignUpBinding
import com.myPackages.searchtrains.databinding.ActivityTrainDetailsBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.EmailSignup.setOnClickListener {
            Toast.makeText(this,"Account Created Successfully",Toast.LENGTH_LONG).show()
            finish()
        }

    }
}