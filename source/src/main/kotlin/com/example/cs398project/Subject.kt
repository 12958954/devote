package com.example.cs398project

interface Subject {
    val observers: MutableList<Observer>

    fun attach(observer: Observer) {
        observers.add(observer)
    }

    fun detach(observer: Observer) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        for (observer in observers) {
            observer.update()
        }
    }
}
