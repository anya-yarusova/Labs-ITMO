# Labs-ITMO
Лабораторная работа по программированию #2 

## Содержание

1. [Содержание](#содержание)
1. [Задание](#задание)
1. [Диаграмма классов реализованной объектной модели](#диаграмма)
1. [Вывод ](#вывод)

## Задание

1. На основе базового класса Pokemon написать свои классы для заданных видов покемонов. Каждый вид покемона должен иметь один или два типа и стандартные базовые характеристики:
   * очки здоровья (HP)
   * атака (attack)
   * защита (defense)
   * специальная атака (special attack)
   * специальная защита (special defense)
   * скорость (speed)
2. Классы покемонов должны наследоваться в соответствии с цепочкой эволюции покемонов. На основе базовых классов PhysicalMove, SpecialMove и StatusMove реализовать свои классы для заданных видов атак.
3. Атака должна иметь стандартные тип, силу (power) и точность (accuracy). Должны быть реализованы стандартные эффекты атаки. Назначить каждому виду покемонов атаки в соответствии с вариантом. Уровень покемона выбирается минимально необходимым для всех реализованных атак.
4. Используя класс симуляции боя Battle, создать 2 команды покемонов (каждый покемон должен иметь имя) и запустить бой.


<p align="center">
    <img src = "https://github.com/anya-yarusova/Labs-ITMO/blob/lab_03/lab-02/sources/task_lab_02.png"/>
</p>


## Диаграмма классов реализованной объектной модели

<p align="center">
    <img src = "https://github.com/anya-yarusova/Labs-ITMO/blob/lab_03/lab-02/sources/diagrama_lab_02.png"/>
</p>

## Вывод 

```
 Eevee Frodo from the team Blue enters the battle!
Mudkip Aragorn from the team Purple enters the battle!
Eevee Frodo uses Psychic. 
Mudkip Aragorn loses 5 hit points.

Mudkip Aragorn uses Facade. 
Eevee Frodo loses 6 hit points.

Eevee Frodo uses Facade. 
Mudkip Aragorn loses 7 hit points.

Mudkip Aragorn struggles. 
Eevee Frodo loses 3 hit points.
Mudkip Aragorn loses 1 hit points.
Mudkip Aragorn faints.
Marshtomp Gandalf from the team Purple enters the battle!
Marshtomp Gandalf uses Psybeam. 
Eevee Frodo loses 6 hit points.
Eevee Frodo faints.
Luvdisc Boromir from the team Blue enters the battle!
Marshtomp Gandalf uses Psybeam. 
Luvdisc Boromir loses 5 hit points.

Luvdisc Boromir uses Acient Power. 
Marshtomp Gandalf loses 2 hit points.

Marshtomp Gandalf uses Psybeam. 
Luvdisc Boromir loses 7 hit points.

Luvdisc Boromir uses Slam. 
Marshtomp Gandalf loses 5 hit points.

Marshtomp Gandalf struggles. 
Luvdisc Boromir loses 4 hit points.
Marshtomp Gandalf loses 1 hit points.
Luvdisc Boromir faints.
Swampert Bilbo from the team Blue enters the battle!
Swampert Bilbo uses Psybeam. 
Marshtomp Gandalf loses 5 hit points.

Marshtomp Gandalf misses

Swampert Bilbo uses Psybeam. 
Marshtomp Gandalf loses 6 hit points.
Marshtomp Gandalf faints.
Umbreon Sauron from the team Purple enters the battle!
Swampert Bilbo misses

Umbreon Sauron uses Psychic. 
Swampert Bilbo loses 6 hit points.

Swampert Bilbo misses

Umbreon Sauron misses

Swampert Bilbo uses Psybeam. 
Umbreon Sauron loses 1 hit points.
Umbreon Sauron isn't affected by PSYCHIC

Umbreon Sauron misses

Swampert Bilbo uses Facade. 
Umbreon Sauron loses 6 hit points.

Umbreon Sauron misses

Swampert Bilbo uses Psybeam. 
Umbreon Sauron loses 1 hit points.
Umbreon Sauron isn't affected by PSYCHIC

Umbreon Sauron uses Facade. 
Swampert Bilbo loses 4 hit points.

Swampert Bilbo uses Facade. 
Umbreon Sauron loses 6 hit points.

Umbreon Sauron uses Shadow Balll. 
Swampert Bilbo loses 4 hit points.
Swampert Bilbo decreases special defense.

Swampert Bilbo misses

Umbreon Sauron uses Psychic. 
Swampert Bilbo loses 5 hit points.

Swampert Bilbo misses

Umbreon Sauron uses Psychic. 
Swampert Bilbo loses 4 hit points.
Swampert Bilbo faints.
Team Blue loses its last Pokemon.
The team Purple wins the battle!


```

