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

    //viewType 기준 정수 값 설정
    private val receive = 1
    private val send = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            // viewType이 1이면 receive의 layout을 binding
            val binding = ChatReceiveBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            ReceiveViewHolder(binding)
        }else {
            // viewType이 2이면 send의 layout을 binding
            val binding = ChatSendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            SendViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SendViewHolder::class.java){
            //holder 가 sendViewHolder 라면, sendlayout의 text에 현재 내가 보낸 Message객체의 message 값을 대입
            val binding = (holder as SendViewHolder).binding
            binding.sendMessageText.text = currentMessage.message
        }else{
            //holder 가 receiveViewHolder 라면, receivelayout의 text에 현재 내가 보낸 Message객체의 message 값을 대입
            val binding = (holder as ReceiveViewHolder).binding
            binding.receiverMessageText.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    //viewType 결정
    override fun getItemViewType(position: Int): Int {
        //현재 대화하는 messageList에 저장되어있는 현재 Message(message, sendUid)을 currentMessage에 대입
        val currentMessage = messageList[position]
        //현재 접속한 사람의 UID와 현재 메세지를 보낸 송신자의 UID가 같으면, send
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
            send
        }else{
            receive
        }
    }

    class SendViewHolder(val binding: ChatSendBinding): RecyclerView.ViewHolder(binding.root)
    class ReceiveViewHolder(val binding: ChatReceiveBinding): RecyclerView.ViewHolder(binding.root)
}