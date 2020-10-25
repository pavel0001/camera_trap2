package com.example.cameratrapv2.screens.MainFragment.TouchHelper

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}