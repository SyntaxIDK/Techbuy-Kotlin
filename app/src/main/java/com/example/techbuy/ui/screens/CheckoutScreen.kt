package com.example.techbuy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
// import androidx.compose.material.RadioButton // Commented out, prefer material3
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.RadioButton // Using material3.RadioButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavHostController) {
    var fullName by remember { mutableStateOf("") }
    var streetAddress by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var emailAddress by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expandedStateDropdown by remember { mutableStateOf(false) }
    var saveShippingInfo by remember { mutableStateOf(false) }
    var selectedPaymentMethod by remember { mutableStateOf("Credit Card") }

    val states = listOf("Kandy", "Colombo", "Badulla", "Matale", "other")
    val paymentOptions = listOf("Credit Card", "PayPal")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Make content scrollable
        ) {
            Text("Shipping Information", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = streetAddress,
                onValueChange = { streetAddress = it },
                label = { Text("Street Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                ExposedDropdownMenuBox(
                    expanded = expandedStateDropdown,
                    onExpandedChange = { expandedStateDropdown = !expandedStateDropdown },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = state,
                        onValueChange = { /* state will be updated by DropdownMenuItem */ },
                        label = { Text("State") },
                        readOnly = true, // Important for dropdowns
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStateDropdown) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor().fillMaxWidth() // menuAnchor for proper positioning
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStateDropdown,
                        onDismissRequest = { expandedStateDropdown = false }
                    ) {
                        states.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    state = selectionOption
                                    expandedStateDropdown = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = zipCode,
                onValueChange = { zipCode = it },
                label = { Text("Zip Code") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = emailAddress,
                onValueChange = { emailAddress = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = saveShippingInfo,
                    onCheckedChange = { saveShippingInfo = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save shipping information for next time?")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Payment Information", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))

            Spacer(modifier = Modifier.height(8.dp))
            Text("Select Payment Method:", style = MaterialTheme.typography.bodyLarge)
            Row(Modifier.fillMaxWidth()) {
                paymentOptions.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { selectedPaymentMethod = option }
                    ) {
                        RadioButton(
                            selected = (option == selectedPaymentMethod),
                            onClick = { selectedPaymentMethod = option }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(option)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (selectedPaymentMethod == "Credit Card") {
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Card Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = expiryDate,
                        onValueChange = { expiryDate = it },
                        label = { Text("Expiry Date (MM/YY)") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { cvv = it },
                        label = { Text("CVV") },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate("order_confirmation") {
                        popUpTo("cart") { inclusive = true } // Clear cart and checkout from backstack
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Confirm Order", style = MaterialTheme.typography.titleMedium)
            }
            Spacer(modifier = Modifier.height(16.dp)) // Add some padding at the bottom
        }
    }
}
