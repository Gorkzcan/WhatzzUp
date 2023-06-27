package com.gorkem.whatzzup.presentation.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.databinding.FragmentEditNoteBinding
import com.gorkem.whatzzup.databinding.FragmentHomeBinding
import com.gorkem.whatzzup.domain.model.Note
import com.gorkem.whatzzup.util.Result
import com.gorkem.whatzzup.util.isConnected
import com.gorkem.whatzzup.util.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding: FragmentEditNoteBinding get() = _binding!!
    private val viewModel by viewModels<EditNoteViewModel>()
    private var receivedNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        setupActionbar()
        setupEditNoteView()
        setupListeners()

        return binding.root
    }

    private fun setupListeners(){
        binding.btnPostSave.setOnClickListener {
            if (isConnected()){
                getFields()
                update()
            }else{
                snackbar(getString(R.string.no_network_warning))
            }
        }
    }

    private fun setupEditNoteView(){
        receivedNote = arguments?.getParcelable("note") as? Note
        binding.let {
            it.tvEditUsername.text = receivedNote!!.username
            it.tvEditDate.text = viewModel.getFormattedDate(receivedNote!!)
            it.etEditTitle.setText(receivedNote!!.title)
            it.etEditContent.setText(receivedNote!!.content)
            Glide.with(this).load(receivedNote!!.imageUrl)
                .into(it.ivEditHeader)
        }
    }

    private fun update(){
        viewModel.updateNote(receivedNote!!).observe(viewLifecycleOwner){
            handleUpdateResponse(it)
            viewModel.resetResult()
        }
    }
    private fun handleUpdateResponse(result: Result){
        when(result){
            is Result.Success -> {
                findNavController().navigate(R.id.action_editNoteFragment_to_homeFragment)
            }
            is Result.Error -> snackbar(result.message)
            else -> {}
        }
    }

    private fun getFields(){
        receivedNote!!.title = binding.etEditTitle.text.toString()
        receivedNote!!.content = binding.etEditContent.text.toString()
    }


    private fun setupActionbar(){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.elevation = 0F
        actionBar?.setDisplayShowTitleEnabled(false)
    }



}