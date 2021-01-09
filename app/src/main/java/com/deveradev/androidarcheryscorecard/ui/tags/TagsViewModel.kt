package com.deveradev.androidarcheryscorecard.ui.tags

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.deveradev.androidarcheryscorecard.MainApplication
import com.deveradev.androidarcheryscorecard.data.ArcherData
import com.deveradev.androidarcheryscorecard.data.ArcherDataRepository
import com.deveradev.androidarcheryscorecard.data.Tag
import com.deveradev.androidarcheryscorecard.utils.Utils
import com.deveradev.androidarcheryscorecard.utils.mutation
import kotlinx.coroutines.launch

class TagsViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var archerData: ArcherData

    val tags = MutableLiveData<ArrayList<Tag>>()
    var selectedTag = MutableLiveData<Tag>()
    var deletedTag: Pair<Tag, Int>? = null

    init {
        Utils.log("TagsViewModel: init")

        this.viewModelScope.launch {
            val mainApplication = application as MainApplication
            val archerDataRepository = mainApplication.daggerDIComponent.getArcheryDataRepository()

            archerData = archerDataRepository.getData(mainApplication)
            Utils.log("ArcherDataRepository: getData")

            for (tag in archerData.tags) {
                Utils.log("${tag.name}")
            }

            tags.value = archerData.tags
        }

    }

    fun deleteTag(tagID: Int) {
        this.tags.mutation {
            it.value?.let { tags ->
                this.deletedTag = tags[tagID] to tagID
                tags.removeAt(tagID)
                Utils.log("TagsViewModel: delete tag $tagID")
            }
        }
    }

    fun undoDeletedTag() {
        this.tags.mutation {
            it.value?.let { tags ->
                deletedTag?.let { deletedTag ->
                    tags.add(deletedTag.second, deletedTag.first)
                    Utils.log("TagsViewModel: undo delete tag ${deletedTag.second}")
                }
                deletedTag = null
            }
        }

    }

}