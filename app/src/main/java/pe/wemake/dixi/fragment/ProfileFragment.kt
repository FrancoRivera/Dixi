package pe.wemake.dixi.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageRequest
import kotlinx.android.synthetic.main.fragment_profile.*
import pe.wemake.dixi.DixiNetworkSingleton
import pe.wemake.dixi.R


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var requestQueue: RequestQueue = DixiNetworkSingleton.getInstance(view.context).requestQueue

        var imageRequest: ImageRequest = ImageRequest("http://placekitten.com/200/300",{
            profile_image.setImageBitmap(it)
        }, 0, 0, null,null, {
            Log.d("Error", it.toString())
        }
            )
        requestQueue.add(imageRequest)
    }


}
