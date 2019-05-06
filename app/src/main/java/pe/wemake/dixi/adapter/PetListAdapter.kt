package pe.wemake.dixi.adapter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageRequest
import kotlinx.android.synthetic.main.recycler_view_pet.view.*
import pe.wemake.dixi.DixiNetworkSingleton
import pe.wemake.dixi.R
import pe.wemake.dixi.fragment.PetListFragmentDirections
import pe.wemake.dixi.model.Pet

class PetListAdapter(context: Context): RecyclerView.Adapter<PetListAdapter.ViewHolder>() {

    private var pets : List<Pet> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_pet, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var imageLoader: ImageLoader = DixiNetworkSingleton.getInstance(context).imageLoader;

        var requestQueue: RequestQueue = DixiNetworkSingleton.getInstance(context).requestQueue;
        with(holder){
            var pet = pets[position]

            var mainImageRequest = ImageRequest(pet.picture, {
                    petImageView.setImageBitmap(it);
                },
                100,
                100,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                {
                    Log.d("Someshit", it.toString())
                }
            )
            petNameTextView.text = pet.name

            itemView.setOnClickListener{
                val action = PetListFragmentDirections.getPetDetail(pet.id, pet.name)
                itemView.findNavController().navigate(action, null )
            }
            requestQueue.add(mainImageRequest)

        }
    }
    override fun getItemCount() = pets.size

    fun setPets(pets: List<Pet>){
        this.pets = pets
        notifyDataSetChanged()
    }
    inner class ViewHolder(petListView: View): RecyclerView.ViewHolder(petListView) {
        var petImageView: ImageView = petListView.pet_image
        var petNameTextView: TextView = petListView.pet_name
    }

}
