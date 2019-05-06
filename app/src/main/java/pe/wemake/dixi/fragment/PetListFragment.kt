package pe.wemake.dixi.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.fragment_pet_list.*
import pe.wemake.dixi.DixiNetworkSingleton
import pe.wemake.dixi.R
import pe.wemake.dixi.adapter.PetListAdapter
import pe.wemake.dixi.model.Pet
import pe.wemake.dixi.viewmodel.PetViewModel

class PetListFragment : Fragment() {

    private lateinit var viewModel: PetViewModel
    private lateinit var adapter: PetListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pet_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set adapter
        adapter = PetListAdapter(view.context)

        recycler_view_pet.apply{
            adapter = adapter
        }
        recycler_view_pet.adapter = adapter
        recycler_view_pet.layoutManager = LinearLayoutManager(view.context)

        //get viewmodel
        viewModel = ViewModelProviders.of(this).get(PetViewModel::class.java)


        // set observer for pets
        viewModel.allPets.observe(this, Observer { pets ->
            adapter.setPets(pets)
        })

        // val safeArgs: PetFragmentArgs by navArgs()
        // safeArgs.petName

        // Get a RequestQueue
        val queue = DixiNetworkSingleton.getInstance(view.context.applicationContext).requestQueue

        val url = "https://anapioficeandfire.com/api/characters/583"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("HTTP RESPONSE", response.toString())
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )

        DixiNetworkSingleton.getInstance(view.context).addToRequestQueue(jsonObjectRequest)

        pet_add_button.setOnClickListener{
            var pet = Pet(viewModel.allPets.value!!.size+1, "Nombre de perro", "http://placekitten.com/300/300" )
            viewModel.insert(pet)

        }

    }

}
