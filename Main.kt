package machine.FirstKotlinTask

import java.util.Scanner

enum class CoffeeMachineState {
    MAIN_MENU, IN_BUY, IN_FILL_WATER, IN_FILL_MILK, IN_FILL_COFFEE, IN_FILL_CUPS
}

enum class CoffeeType(val water: Int, val milk: Int, val coffee: Int, val cost: Int) {
    AMERICANO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6)
}

class CoffeeMachine(var water: Int, var milk: Int, var coffee: Int, var cups: Int, var money: Int) {
    var state: CoffeeMachineState = CoffeeMachineState.MAIN_MENU

    fun printState() {
        println("The coffee machine has:")
        println("${this.water} of water")
        println("${this.milk} of milk")
        println("${this.coffee} of coffee beans")
        println("${this.cups} of disposable cups")
        println("$${this.money} of money")
        println()
    }

    fun makeCoffee(coffee: CoffeeType) {
        if (this.water >= coffee.water && this.milk >= coffee.milk && this.coffee >= coffee.coffee && this.cups > 0) {
            this.water -= coffee.water
            this.milk -= coffee.milk
            this.coffee -= coffee.coffee
            this.cups -= 1
            this.money += coffee.cost
            println("I have enough resources, making you a coffee!")
        } else {
            println(when {
              this.water < coffee.water -> "Sorry, not enough water!"
              this.milk < coffee.milk -> "Sorry, not enough milk!"
              this.coffee < coffee.coffee -> "Sorry, not enough coffee beans!"
              this.cups < 1 -> "Sorry, not enough cups!"
                else -> "Not enough resources"
            })
        }
        this.state = CoffeeMachineState.MAIN_MENU
    }

    fun take() {
        println("I gave you $${this.money}")
        this.money = 0
    }

    fun proceedInput(input: String): Boolean {
        when (this.state) {
            CoffeeMachineState.MAIN_MENU -> {
                when (input) {
                    "buy" -> {
                        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: >")
                        this.state = CoffeeMachineState.IN_BUY
                    }
                    "fill" -> {
                        println("Write how many ml of water do you want to add: >")
                        this.state = CoffeeMachineState.IN_FILL_WATER
                    }
                    "take" -> this.take()
                    "remaining" -> this.printState()
                    "exit" -> return false
                }
            }
            CoffeeMachineState.IN_BUY -> {
                if (input == "back") {
                    this.state = CoffeeMachineState.MAIN_MENU
                } else {
                    when (Integer.parseInt(input)) {
                        1 -> this.makeCoffee(CoffeeType.AMERICANO)
                        2 -> this.makeCoffee(CoffeeType.LATTE)
                        3 -> this.makeCoffee(CoffeeType.CAPPUCCINO)
                        else -> return false
                    }
                }
            }
            CoffeeMachineState.IN_FILL_WATER -> {
                this.water += Integer.parseInt(input)
                println("Write how many ml of milk do you want to add: >")
                this.state = CoffeeMachineState.IN_FILL_MILK
            }
            CoffeeMachineState.IN_FILL_MILK -> {
                this.milk += Integer.parseInt(input)
                println("Write how many grams of coffee beans do you want to add: >")
                this.state = CoffeeMachineState.IN_FILL_COFFEE
            }
            CoffeeMachineState.IN_FILL_COFFEE -> {
                this.coffee += Integer.parseInt(input)
                println("Write how many disposable cups of coffee do you want to add: >")
                this.state = CoffeeMachineState.IN_FILL_CUPS
            }
            CoffeeMachineState.IN_FILL_CUPS -> {
                this.cups += Integer.parseInt(input)
                this.state = CoffeeMachineState.MAIN_MENU
            }

        }
        return true;
    }

    fun prepare(): CoffeeMachine {
        if (this.state == CoffeeMachineState.MAIN_MENU) {
            println("Write action (buy, fill, take, remaining, exit): >")
            println()
        }
        return this
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val machine = CoffeeMachine(400, 540, 120, 9, 550)
    var active = true
    while (active) {
        active = machine.prepare().proceedInput(scanner.nextLine())
    }
}
