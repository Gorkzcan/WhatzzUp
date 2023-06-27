package com.gorkem.whatzzup.presentation.newNote

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.gorkem.whatzzup.R
import com.gorkem.whatzzup.databinding.FragmentHomeBinding
import com.gorkem.whatzzup.databinding.FragmentNewNoteBinding
import com.gorkem.whatzzup.presentation.home.HomeFragmentDirections
import com.gorkem.whatzzup.util.Constants.Companion.GALLERY_CODE
import com.gorkem.whatzzup.util.isConnected
import com.gorkem.whatzzup.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.gorkem.whatzzup.util.Result
import kotlinx.coroutines.selects.select

@AndroidEntryPoint
class NewNoteFragment : Fragment() {
    private var _binding: FragmentNewNoteBinding? = null
    private val binding: FragmentNewNoteBinding get() = _binding!!
    private val viewModel by viewModels<NewNoteVM>()
    private var imageUri: Uri? = null
    private lateinit var title: String
    private lateinit var content: String
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        setupActionbar()
        setupViews()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectImage()
        saveNote()
    }

    private fun setupActionbar(){

            val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
            actionBar?.elevation = 0F
            actionBar?.setDisplayShowTitleEnabled(false)
        }

    private fun setupViews(){
        viewModel.userData.observe(viewLifecycleOwner){userData ->
            binding.tvPostUsername.text = userData.username
            username = userData.username
        }
    }

    fun saveNote(){
        binding.btnPostSave.setOnClickListener {
            if(isConnected()){
                getFields()
                lifecycle.coroutineScope.launch{
                    viewModel.saveNote(title, content, imageUri, username)
                }
                viewModel.result.observe(viewLifecycleOwner){
                    handleSaveResponse(it)
                }
                viewModel.resetResult()
            }else{
                snackbar(getString(R.string.no_network_warning))
            }
        }

    }

    fun selectImage(){
        binding.ivAddPhoto.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), GALLERY_CODE)
        }

    }



    private fun getFields(){
        title = binding.etPostTitle.text.toString().trim()
        content = binding.etPostTitle.text.toString().trim()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.data
                binding.ivHeader.setImageURI(imageUri)
            }
        }
    }

    private fun handleSaveResponse(result: Result) {
        when (result) {
            is Result.Success -> {
                navigateHomeFragment()
            }
            is Result.Error -> {
                snackbar(result.message)
            }
            else-> {
                findNavController().popBackStack()
            }
        }
    }

    private fun navigateHomeFragment(){
        val action = NewNoteFragmentDirections.actionNewNoteFragmentToHomeFragment()
        findNavController().navigate(action)

    }


}