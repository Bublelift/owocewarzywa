package com.example.owocewarzywa.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.owocewarzywa.R
import com.example.owocewarzywa.model.ChatViewModel
import com.example.owocewarzywa.recyclerview.item.PersonItem
import com.example.owocewarzywa.utils.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_people.*

class PeopleFragment : Fragment() {

    private lateinit var userListenerRegistration: ListenerRegistration

    private var shouldInitRecyclerView = true

    private lateinit var peopleSection: Section

    private val chatViewModel: ChatViewModel by activityViewModels()

    private var displayedItems: List<Item> = emptyList()

    private val onItemClick = OnItemClickListener {item, view ->
        if (item is PersonItem) {
            chatViewModel.setChatUser(item.person.name, item.userId)
            findNavController().navigate(R.id.action_peopleFragment_to_chatFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userListenerRegistration = FirestoreUtil.addUsersListener(this.requireActivity(), this::updateRecyclerView)
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        people_loading.visibility = View.VISIBLE
        super.onViewCreated(view, savedInstanceState)
        people_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                chatViewModel.search(s.toString())
                updateRecyclerView(displayedItems)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirestoreUtil.removeListener(userListenerRegistration)
        shouldInitRecyclerView = true
    }

    private fun updateRecyclerView(items: List<Item>){
        people_loading.visibility = View.VISIBLE
        if (displayedItems.isEmpty()) displayedItems = items
        fun init() {
            recycler_view_people.apply{
                layoutManager = LinearLayoutManager(this@PeopleFragment.context)
                adapter = GroupAdapter<ViewHolder>().apply{
                    peopleSection = Section(items)
                    add(peopleSection)
                    setOnItemClickListener(onItemClick)
                }
            }
            shouldInitRecyclerView = false
        }

        fun filterItems(item: Item): Boolean {
            if (item is PersonItem) {
                return item.person.name.toString().lowercase().contains(chatViewModel.search.value!!.lowercase()) ||
                        item.id.toString().lowercase().contains(chatViewModel.search.value!!.lowercase())
            }
            return true
        }

        fun updateItems() {
            var itemsFiltered = mutableListOf<Item>()
            if (chatViewModel.search.value != null) {
                items.forEach{
                    if (filterItems(it))
                        itemsFiltered.add(it)
                }
            }
            else itemsFiltered = items as MutableList<Item>
            val resultItems: List<Item> = itemsFiltered
            peopleSection.update(resultItems)
        }


        if (shouldInitRecyclerView)
            init()
        else
            updateItems()
        
        people_loading.visibility = View.GONE
    }

}