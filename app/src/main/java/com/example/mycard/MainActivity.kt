package com.example.mycard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycard.Project.MVVM.View.MainWindow
import com.example.mycard.ui.theme.MyCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainWindow()
                }
            }
        }
    }
}
//dict.1.1.20220513T141149Z.c25bbf6feb708385.c293ce128f4bb0140d1f6952536927b054117372

