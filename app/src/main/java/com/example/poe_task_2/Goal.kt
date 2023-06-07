package com.example.poe_task_2

class Goal {
    companion object {
        var minimumGoal: Int = 0
        var maximumGoal: Int = 0
    }

    fun getMinimumGoal(): Int {
        return minimumGoal
    }

    fun setMinimumGoal(minimumGoal: Int) {
        Goal.minimumGoal = minimumGoal
    }

    fun getMaximumGoal(): Int {
        return maximumGoal
    }

    fun setMaximumGoal(maximumGoal: Int) {
        Goal.maximumGoal = maximumGoal
    }
}