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

        //FriendslistActivity의 ItemView를 누를 시 받는 해당 유저의 Intent 값
        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uId").toString()

        //현재 접속자 Uid
        val senderUid = userAuth.currentUser?.uid

        //송신자 방(자식 한 번 더 만드는 것)
        senderRoom = receiverUid + senderUid

        //수신자 방(자식 한 번 더 만드는 것)
        receiverRoom = senderUid + receiverUid

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = receiverName

        binding.btnSend.setOnClickListener {
            val message = binding.edtMessage.text.toString()
            val messageObject = Message(message, senderUid)

            //메세지 저장 - 송신자의 방에 Message객체가 잘 삽입 된다면, 수신자의 방에도 Message 객체 삽입
            database.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    database.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }
            //입력 이후 입력 창 초기화
            binding.edtMessage.setText("")
        }

        //메세지 리스트 - 사용자가 송신자일 때의 Database에 접근해서 해당 값을 가져온 후 그 전 messageList 초기화하고,
        //최신 대화를 다시 불러와서 messageList에 넣도록 한다.
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