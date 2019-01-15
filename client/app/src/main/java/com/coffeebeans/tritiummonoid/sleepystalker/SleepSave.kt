package com.coffeebeans.tritiummonoid.sleepystalker

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.util.Consumer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SleepSave.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SleepSave.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SleepSave : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sleep_save, container, false)
        listener?.setSleepModelConsumer(Consumer {
            val model = it
            val txtTime = view.findViewById(R.id.txtSleepValueTime) as TextView
            val txtFood = view.findViewById(R.id.txtSleepValueFood) as TextView
            val txtStress = view.findViewById(R.id.txtSleepValueStress) as TextView
            val txtExercise = view.findViewById(R.id.txtSleepValueExercise) as TextView
            val txtMood = view.findViewById(R.id.txtSleepValueMood) as TextView
            val btnSave = view.findViewById(R.id.btnSleepSave) as Button
            txtTime.text = model.datetime.format()
            txtFood.text = model.food.name
            txtStress.text = model.stress.name
            txtExercise.text = model.exercise.name
            txtMood.text = model.mood.name
            btnSave.setOnClickListener {
                try {
                    SleepStore(it.context).save(model)
                    Log.v("SLEEP_SAVE", "SAVED")
                    listener?.onSaveSuccess()
                } catch (e: Exception) {
                    Log.e("SLEEP_SAVE", e.message)
                    listener?.onSaveError()
                }
            }
        })
        return view
    }

    private fun Date.format(): String {
        return formatter.format(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener?.setSleepModelConsumer(null)
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun setSleepModelConsumer(consumer: Consumer<SleepModel>?)
        fun onSaveSuccess()
        fun onSaveError()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SleepSave.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SleepSave().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
