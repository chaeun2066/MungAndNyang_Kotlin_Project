package com.example.mungandnyang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mungandnyang.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var receiverName: String
    lateinit var receiverUid: String
    lateinit var userAuth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var receiverRoom : String
    lateinit var senderRoom : String
    lateinit var messageList: MutableList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        messageList = mutableListOf<Message>()
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        //리사이클러뷰
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        setSupportActionBar(binding.chatToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uId").toString()

        //접속자 Uid
        val senderUid = userAuth.currentUser?.uid

        //송신자 방
        senderRoom = receiverUid + senderUid
        //수신자 방
        receiverRoom = senderUid + receiverUid

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = receiverName

        binding.btnSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            val messageObject = Message(message, senderUid)

            //메세지 저장
            database.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    database.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }
            binding.edtMessage.setText("")
        }

        //메세지 리스트
        database.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}