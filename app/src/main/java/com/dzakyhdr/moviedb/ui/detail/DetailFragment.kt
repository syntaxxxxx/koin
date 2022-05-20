package com.dzakyhdr.moviedb.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dzakyhdr.moviedb.R
import com.dzakyhdr.moviedb.data.local.favorite.MovieEntity
import com.dzakyhdr.moviedb.databinding.FragmentDetailBinding
import com.dzakyhdr.moviedb.utils.urlImage
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetail(args.movieId)

        binding.ivBack.setOnClickListener {
            it.findNavController().popBackStack()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.loadingContainer.visibility = View.VISIBLE
            } else {
                binding.loadingContainer.visibility = View.GONE
            }
        }



        viewModel.detail.observe(viewLifecycleOwner) { movieDetail ->

            Glide.with(binding.ivBackdrop)
                .load(urlImage + movieDetail?.backdropPath)
                .error(R.drawable.ic_broken)
                .into(binding.ivBackdrop)

            Glide.with(binding.ivPoster)
                .load(urlImage + movieDetail?.posterPath)
                .error(R.drawable.ic_broken)
                .into(binding.ivPoster)

            binding.apply {
                tvTitle.text = movieDetail?.title
                tvVoteCount.text = movieDetail?.voteCount.toString()
                tvOverview.text = movieDetail?.overview
                movieDetail?.voteAverage.let {
                    if (it != null) {
                        rbRating.rating = (it / 2).toFloat()
                    }
                }

                if (movieDetail?.releaseDate != null && movieDetail.releaseDate.isNotBlank()) {
                    tvReleaseDate.text = movieDetail.releaseDate
                } else {
                    tvReleaseDate.visibility = View.GONE
                }
            }

            val movieEntity = MovieEntity(
                movieDetail?.id!!,
                movieDetail.backdropPath,
                movieDetail.overview,
                movieDetail.posterPath,
                movieDetail.releaseDate,
                movieDetail.title,
                movieDetail.voteAverage,
                movieDetail.voteCount
            )

            viewModel.showUserIsFavorite(movieEntity)

            binding.btnFavorite.setOnClickListener {
                viewModel.checkFavoriteUser(movieEntity)
            }

        }

        viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
            if (isFavorite) {
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.btnFavorite.context,
                        R.drawable.ic_favorite_true
                    )
                )
            } else {
                binding.btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.btnFavorite.context,
                        R.drawable.ic_favorite_false
                    )
                )
            }
        }

        viewModel.errorStatus.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShown()
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}