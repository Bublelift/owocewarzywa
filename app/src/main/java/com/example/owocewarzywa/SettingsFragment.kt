package com.example.owocewarzywa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
//    private val practiceData: PracticeViewModel by activityViewModels()

    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinners()
        binding!!.settingsProgress.setOnClickListener {
            //findNavController().navigate(R.id.action_practiceSettingsFragment_to_practiceSelect)
            goPractice()
        }
    }

    private fun initSpinners() {
        var languages = ArrayList<String>()
        languages.apply {
            add(resources.getString(R.string.lang_pl))
            add(resources.getString(R.string.lang_en))
            add(resources.getString(R.string.lang_ge))
        }
        val langDataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, languages)
        langDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding!!.spinnerLanguage.adapter = langDataAdapter;

        var topics = ArrayList<String>()
        topics.apply {
            add("Standardowa")
            add("Duża")
        }
        val topDataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, topics)
        topDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding!!.spinnerTopic.adapter = topDataAdapter;

        var diffuculties = ArrayList<String>()
        diffuculties.apply {
            add("Jasny")
            add("Ciemny")
        }
        val difDataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                diffuculties
            )
        difDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding!!.spinnerDifficulty.adapter = difDataAdapter;
    }

    private fun goPractice() {
        findNavController().navigateUp()
    }
}