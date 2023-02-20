package com.example.notes.ui.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notes.ui.NoteViewModel

@Composable
fun SearchText(viewModel: NoteViewModel){

    var search by remember {
        mutableStateOf("")
    }

    MaterialTheme {

        Column() {
            TextField(value = search,
                onValueChange = { search = it
                                viewModel.search(search)},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(5.dp, 0.dp),
                textStyle = TextStyle(fontSize = 52.sp),
                placeholder = {
                    Text(
                        "Search", fontSize = 52.sp, color = Color(0xff9A9A9A)
                    )
                })

        }
    }
}
