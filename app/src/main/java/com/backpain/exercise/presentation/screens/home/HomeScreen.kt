package com.backpain.exercise.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.backpain.exercise.R
import com.backpain.exercise.presentation.theme.BackPainExerciseAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.home_title)) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: Replace with actual exercise sets from ViewModel
            item {
                ExerciseSetCard(
                    title = stringResource(R.string.beginner_set),
                    description = "Gentle movements for pain relief",
                    exerciseCount = 4,
                    durationMinutes = 15,
                    onClick = { /* TODO: Navigate to exercise set */ }
                )
            }
            
            item {
                ExerciseSetCard(
                    title = stringResource(R.string.core_strength_set),
                    description = "Building support muscles",
                    exerciseCount = 4,
                    durationMinutes = 20,
                    onClick = { /* TODO: Navigate to exercise set */ }
                )
            }
            
            item {
                ExerciseSetCard(
                    title = stringResource(R.string.flexibility_set),
                    description = "Improving range of motion",
                    exerciseCount = 4,
                    durationMinutes = 18,
                    onClick = { /* TODO: Navigate to exercise set */ }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseSetCard(
    title: String,
    description: String,
    exerciseCount: Int,
    durationMinutes: Int,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$exerciseCount exercises",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "$durationMinutes min",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    BackPainExerciseAppTheme {
        // TODO: Create preview with mock NavController
    }
}
