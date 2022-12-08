package com.example.mungandnyang

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mungandnyang.databinding.ChatReceiveBinding
import com.example.mungandnyang.databinding.ChatSendBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: MutableList<Message>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val receive = 1
    private val send = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            val binding = ChatReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ReceiveViewHolder(binding)
        }else {
            val binding = ChatSendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            SendViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SendViewHolder::class.java){
            val binding = (holder as SendViewHolder).binding
            binding.sendMessageText.text = currentMessage.message
        }else{
            val binding = (holder as ReceiveViewHolder).binding
            binding.receiverMessageText.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
            send
        }else{
            receive
        }
    }

    class SendViewHolder(val binding: ChatSendBinding): RecyclerView.ViewHolder(binding.root)
    class ReceiveViewHolder(val binding: ChatReceiveBinding): RecyclerView.ViewHolder(binding.root)
}