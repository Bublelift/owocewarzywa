package com.example.owocewarzywa.practice

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.owocewarzywa.R
import com.example.owocewarzywa.databinding.FragmentPracticeSettingsBinding
import com.example.owocewarzywa.model.PracticeViewModel


class PracticeSettingsFragment : Fragment() {

    private val practiceData: PracticeViewModel by activityViewModels()

    private var binding: FragmentPracticeSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentPracticeSettingsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinners()
        binding!!.settingsProgress.setOnClickListener {
            practiceData.setPracticeSettings(
                binding!!.spinnerLanguage.selectedItem.toString(),
                binding!!.spinnerTopic.selectedItem.toString(),
                binding!!.spinnerDifficulty.selectedItem.toString()
            )
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
            add(resources.getString(R.string.tpc_nat))
            add(resources.getString(R.string.tpc_lif))
            add(resources.getString(R.string.tpc_tec))
        }
        val topDataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, topics)
        topDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding!!.spinnerTopic.adapter = topDataAdapter;

        var diffuculties = ArrayList<String>()
        diffuculties.apply {
            add(resources.getString(R.string.dif_ez))
            add(resources.getString(R.string.dif_hd))
            add("Egzamin z elektrotechniki u LJ")
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
        practiceData.setPracticeSettings(
            binding!!.spinnerLanguage.selectedItem.toString(),
            binding!!.spinnerTopic.selectedItem.toString(),
            binding!!.spinnerDifficulty.selectedItem.toString()
        )
        when (practiceData.type.value) {
            "unscramble" -> findNavController().navigate(R.id.action_practiceSettingsFragment_to_unscrambleFragment)
            "quiz" -> findNavController().navigate(R.id.action_practiceSettingsFragment_to_quizFragment)
            "memo" -> findNavController().navigate(R.id.action_practiceSettingsFragment_to_memoFragment)
            "fill" -> findNavController().navigate(R.id.action_practiceSettingsFragment_to_fillFragment)
            "flashcards" -> findNavController().navigate(R.id.action_practiceSettingsFragment_to_flashcardFragment)
            else -> Toast.makeText(requireContext(), "TODO przechodzenie do ćwiczenia", Toast.LENGTH_SHORT).show()
        }
    }
}