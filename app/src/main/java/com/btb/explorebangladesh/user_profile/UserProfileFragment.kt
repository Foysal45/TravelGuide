package com.btb.explorebangladesh.user_profile
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.btb.explorebangladesh.R
import com.btb.explorebangladesh.activity.toast
import com.btb.explorebangladesh.data.model.user_info.info_update.InfoUpdateRequest
import com.btb.explorebangladesh.databinding.FragmentUserProfileBinding
import com.btb.explorebangladesh.presentation.activities.auth.AuthActivity
import com.btb.explorebangladesh.presentation.fragments.base.BaseFragment
import com.btb.explorebangladesh.user_profile.ViewModel.UserInfoViewModel
import com.btb.explorebangladesh.util.FileUtils
import com.btb.explorebangladesh.util.alert
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<UserInfoViewModel, FragmentUserProfileBinding>(
    R.layout.fragment_user_profile
) {

    override val viewModel by viewModels<UserInfoViewModel>()
    override fun initializeViewBinding(view: View) = FragmentUserProfileBinding.bind(view)
    private var profileImgUrl: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        if (viewModel.hasLoggedIn()) {
            viewModel.getUserInformation()
        } else {
            navigateToAuthActivity()
        }

        fetchProfileData()
        initClickLister()
    }

    //function for fetching the user data from api and show it in the exact matching field
    private fun fetchProfileData() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.articles.observe(viewLifecycleOwner) { userInfo ->
            binding.progressBar.visibility = View.GONE
            binding.userNameEdit.setText(userInfo.fullName)
            binding.userPhoneEdit.setText(userInfo.phone)
            binding.userGenderEdit.setText(userInfo.gender)
            binding.userCountryEdit.setText(userInfo.country)
            binding.userCountryPostcodeEdit.setText(userInfo.postcode)
            binding.userNationalityEdit.setText(userInfo.nationality)
            binding.userAddressEdit.setText(userInfo.address)
            binding.userEmailEdit.setText(userInfo.email)
            Toast.makeText(activity, "userName = ${userInfo.fullName}", Toast.LENGTH_SHORT).show()

            if (binding.userEmailEdit.text != null) {
                binding.userEmailEdit.isClickable = false
                binding.userEmailEdit.isFocusable = false
                binding.userEmailEdit.isCursorVisible = false
            }

            if (userInfo.image.isNotEmpty()) {
               binding.profilePic.let { view ->
                    Glide.with(view)
                        .load(userInfo.image)
                        .apply(RequestOptions().placeholder(R.drawable.ic_person_circle).error(R.drawable.ic_person_circle).circleCrop())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(view)
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error ?: return@observe

        }
    }

    private fun initClickLister() {

        binding.ivBackMyProfile.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.profilePicEdit.setOnClickListener {
            pickUpImage()
        }
        binding.updateBtnUserInformation.setOnClickListener {
            context?.toast("Button clicked")
            //employeeInformationUpdate()
            uploadProfilePhoto("btb","btb_user/profile",profileImgUrl)
        }
    }

    //pick the image from file/gallery
    private fun pickUpImage() {

        if (!isStoragePermissions()) {
            return
        }
        try {
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
            }.also {
                getImages.launch(it)
            }
        } catch (e: Exception) {
            Timber.d(e)
            context?.toast("No Application found to handle this action")
        }
    }

    private fun isStoragePermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val storagePermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                val storagePermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (storagePermissionRationale) {
                    permission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    permission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                false
            } else {
                true
            }
        } else {
            return true
        }
    }

    private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { hasPermission ->
        if (hasPermission) {

        } else {
            val storagePermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (storagePermissionRationale) {
                alert("Permission Required", "App required Storage permission to function properly. Please grand permission.", true, "Give Permission", "Cancel") {
                    if (it == AlertDialog.BUTTON_POSITIVE) {
                        isStoragePermissions()
                    }
                }.show()
            } else {
                alert("Permission Required", "Please go to Settings to enable Storage permission. (Settings-apps--permissions)", true, "Settings", "Cancel") {
                    if (it == AlertDialog.BUTTON_POSITIVE) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${requireContext().packageName}")).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                    }
                }.show()
            }
        }
    }

    private val getImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val fileUri = result?.data?.data
            Timber.d("FileUri: $fileUri")
            val actualPath = FileUtils(requireContext()).getPath(fileUri)
            Timber.d("FilePath: $actualPath")
            profileImgUrl = actualPath
            Toast.makeText(activity, "name = $profileImgUrl", Toast.LENGTH_LONG).show()
            binding.profilePic.let { view ->
                Glide.with(view)
                    .load(profileImgUrl)
                    .apply(RequestOptions().placeholder(R.drawable.ic_person_circle).error(R.drawable.ic_person_circle).circleCrop())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .into(view)
            }
            uploadProfilePhoto("btb","btb_user/profile",profileImgUrl)
        }

    }

    //function for update user information data
   /* private fun employeeInformationUpdate(){
        val requestBody = InfoUpdateRequest(
            binding.userNameEdit.text?.trim().toString(),
            binding.userPhoneEdit.text?.trim().toString(),
            binding.userNameEdit.text?.trim().toString(),
            binding.userCountryEdit.text?.trim().toString(),
            binding.userCountryPostcodeEdit.text?.trim().toString(),
            binding.userNationalityEdit.text?.trim().toString(),
            binding.userEmailEdit.text?.trim().toString(),
            binding.userAddressEdit.text?.trim().toString(),
        )

        viewModel.employeeInformationUpdate(requestBody).observe(viewLifecycleOwner, Observer {
                if (it) {
                    context?.toast("Updated Successfully")
                }
            })
        
    }*/

    //function for uploading user profile Image
    private fun uploadProfilePhoto(fileName: String, imagePath: String, fileUrl: String) {
        
        if (profileImgUrl.isNotEmpty()) {
            viewModel.uploadProfilePhoto(fileName, imagePath, fileUrl)
                .observe(viewLifecycleOwner, Observer {flag ->
                    context?.toast("image updated")
                   /*if (it) {
                        context?.toast("image updated")
                        viewModel.updateProfilePic.value = true
                    }*/
                })
        }
    }

    //if user is not logged in than this fragment will go to login Activity
    private fun navigateToAuthActivity() {
        requireActivity().startActivity(
            Intent(requireActivity(), AuthActivity::class.java)
        )
    }

}