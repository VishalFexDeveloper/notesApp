package com.example.notesappimport android.annotation.SuppressLintimport android.content.Contextimport android.content.Intentimport android.content.SharedPreferencesimport android.graphics.BitmapFactoryimport android.net.Uriimport android.view.LayoutInflaterimport android.view.Viewimport android.view.ViewGroupimport android.widget.ImageButtonimport android.widget.TextViewimport android.widget.Toastimport androidx.recyclerview.widget.RecyclerViewimport com.google.android.material.imageview.ShapeableImageViewimport org.w3c.dom.Textclass NotesAdapter(private var notes: List<NotesData>,val context: Context):RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {    private val db:NotesDatabaseHelper = NotesDatabaseHelper(context)    class NotesViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){        val titleText = itemView.findViewById<TextView>(R.id.titleTextview)        val contentText = itemView.findViewById<TextView>(R.id.contentTextview)        val img :ShapeableImageView = itemView.findViewById(R.id.icon_img)        val updateBtn = itemView.findViewById<ImageButton>(R.id.upDataBtn)        val deleteBtn  = itemView.findViewById<ImageButton>(R.id.deleteBtn)    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {      return NotesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false))    }    override fun getItemCount(): Int = notes.size    @SuppressLint("SuspiciousIndentation")    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {        val note = notes[position]        holder.titleText.text = note.title        holder.contentText.text =note.content        val bitmap = BitmapFactory.decodeByteArray(note.img, 0, note.img.size)        holder.img.setImageBitmap(bitmap)        holder.updateBtn.setOnClickListener {            val intent = Intent(holder.itemView.context,UpDateActivity::class.java)                intent.putExtra("note_Id",note.id)            holder.itemView.context.startActivity(intent)        }        holder.deleteBtn.setOnClickListener {            db.deleteNote(note.id)            refreshData(db.getAllNotes())            Toast.makeText(holder.itemView.context, " ${note.id} Notes Deleted", Toast.LENGTH_SHORT).show()        }    }    @SuppressLint("NotifyDataSetChanged")    fun refreshData(newNotes:List<NotesData>){        notes = newNotes        notifyDataSetChanged()    }}