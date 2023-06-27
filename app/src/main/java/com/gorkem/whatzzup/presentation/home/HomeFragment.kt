package com.gorkem.whatzzup.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.databinding.FragmentHomeBinding
import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.util.Result
import com.gorkem.whatzzup.util.SwipeToDeleteCallback
import com.gorkem.whatzzup.util.hide
import com.gorkem.whatzzup.util.show
import com.gorkem.whatzzup.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val viewModel by viewModels<HomeVM>()
    private lateinit var adapter:NoteAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        var toolbar = binding.toolbar // Toolbar bileşenini bağlayın
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar) // Toolbar'ı eylem çubuğu olarak ayarlay
        setupActionbar()
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setNoNotesView()
        lifecycle.coroutineScope.launch {
            loadNotes()
        }

    }

    private fun setupListeners(){
        binding.fabAdd.setOnClickListener {
            startNewNote()
        }
    }

    private fun startNewNote(){
        val action = HomeFragmentDirections.actionHomeFragmentToNewNoteFragment()
        findNavController().navigate(action)
    }

    private val itemTouchHelperCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val note = adapter.noteList[position]

            showDeleteConfirmationDialog(position, note)
        }
    }

    private fun handleDeleteResponse(result: Result, position: Int, note:Note) {
        when (result) {
            is Result.Success -> {
                showDeleteConfirmationDialog(position, note)
            }
            is Result.Error -> snackbar(result.message)
            else -> {}
        }
    }

    private fun showNoNotesView(result:Result.Error){
        snackbar(result.message)
        setNoNotesView()
    }

    private fun showNotesView(result:Result.Success){
        setNotesView()
        setupNotesList(result.data as List<Note>)
    }

    private fun setNotesView() {
        binding.rvNotes.show()
        binding.tvNoNotes.hide()
    }

    private fun setNoNotesView() {
        binding.rvNotes.hide()
        binding.tvNoNotes.show()
    }

    private fun setupNotesList(notes: List<Note>) {
        adapter = NoteAdapter(this, object: NoteAdapter.OnItemClickListener{
            override fun onItemClick(note: Note) {
                val action = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(note)
                findNavController().navigate(action)
            }
        })
        binding.rvNotes.adapter = adapter
        adapter.setNotes(notes)

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvNotes)
    }

    private fun setupActionbar(){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.elevation = 0F
        actionBar?.setDisplayShowTitleEnabled(false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.actionSignOut ->{
                logout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout(){
        viewModel.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun loadNotes(){
        viewModel.loadNotes().collect{result ->
            when(result){
                is Result.Success -> {
                    viewModel.stopLoading()
                    if ((result.data as List<*>).isNotEmpty()){
                        showNotesView(result)
                    }else{
                        setNoNotesView()
                    }
                }
                is Result.Error -> {
                    viewModel.stopLoading()
                    showNoNotesView(result)
                }
                Result.Loading -> {
                    binding.tvNoNotes.hide()
                    viewModel.startLoading()
                }
            }
        }
    }

    private fun confirmDelete(position: Int, note: Note) {
        viewModel.deleteNote(note).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    adapter.noteList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    if (adapter.noteList.isEmpty()) {
                        setNoNotesView()
                    }
                }
                is Result.Error -> snackbar(result.message)
                else -> {}
            }
        }
    }

    private fun showDeleteConfirmationDialog(position: Int, note: Note) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_dialog_title)
            .setMessage(R.string.delete_dialog_message)
            .setPositiveButton(R.string.delete_dialog_positive_button) { _, _ ->
                confirmDelete(position, note)
            }
            .setNegativeButton(R.string.delete_dialog_negative_button) { _, _ ->
                adapter.notifyItemChanged(position)
            }
            .create()

        dialog.show()
    }

}