package com.example.gonggangam.Activity


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


import com.example.gonggangam.databinding.ActivityDiaryWriteEmojiBinding
import java.io.ByteArrayOutputStream


class DiaryWriteEmojiActivity : AppCompatActivity()
{


    lateinit var binding: ActivityDiaryWriteEmojiBinding



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteEmojiBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val mainInt = intent



        binding.diaryWriteEmojiView.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap = (binding.diaryWriteEmojiEmojiIv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()
                Log.d("웅", mainInt.getIntExtra("year",0).toString()+mainInt.getIntExtra("month",0).toString()+mainInt.getIntExtra("day",0).toString())
                intent.putExtra("state", "행복해요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))


                startActivity(intent)
                finish()

            }

        }

        binding.diaryWriteEmojiView02.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)
                val chageIntent=Intent()
                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji02Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "즐거워요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))






                startActivity(intent)
                finish()
            }

        }

        binding.diaryWriteEmojiView03.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji03Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "그냥 그래요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()
            }

        }

        binding.diaryWriteEmojiView04.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji04Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()


                intent.putExtra("state", "지루해요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()

            }
        }

        binding.diaryWriteEmojiView05.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji05Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "슬퍼요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()
            }
        }

        binding.diaryWriteEmojiView06.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji06Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "화나요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()
            }
        }

        binding.diaryWriteEmojiView07.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji07Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "짜증나요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()
            }
        }

        binding.diaryWriteEmojiView08.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji08Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "우울해요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()
            }
        }

        binding.diaryWriteEmojiView09.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji09Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "창피해요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()

            }
        }

        binding.diaryWriteEmojiView10.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji10Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "설레요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()

            }
        }

        binding.diaryWriteEmojiView11.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.VISIBLE


            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji11Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "복잡해요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()

            }
        }

        binding.diaryWriteEmojiView12.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji04Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji04Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji05Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji05Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji06Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji06Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji07Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji07Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji08Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji08Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji09Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji09Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji10Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji10Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji11Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji11Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiDarkEmoji12Iv.visibility = View.GONE
            binding.diaryWriteEmojiEmoji12Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiSelectedBtn.setOnClickListener {
                val intent = Intent(this, DiaryWriteActivity::class.java)

                val stream = ByteArrayOutputStream()
                val bitmap =
                    (binding.diaryWriteEmojiEmoji12Iv.getDrawable() as BitmapDrawable).bitmap
                val scale = (1024 / bitmap.width.toFloat())
                val image_w = (bitmap.width * scale).toInt()
                val image_h = (bitmap.height * scale).toInt()
                val resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true)
                resize.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray: ByteArray = stream.toByteArray()

                intent.putExtra("state", "궁금해요")
                intent.putExtra("image", byteArray)
                intent.putExtra("year",mainInt.getIntExtra("year",0))
                intent.putExtra("month",mainInt.getIntExtra("month",0))
                intent.putExtra("day",mainInt.getIntExtra("day",0))
                if(!mainInt.getStringExtra("content").isNullOrEmpty()) intent.putExtra("content",mainInt.getStringExtra("content"))
                if(!mainInt.getStringExtra("shareAgree").isNullOrEmpty()) intent.putExtra("shareAgree",mainInt.getStringExtra("shareAgree"))
                if(!mainInt.getStringExtra("img").isNullOrEmpty()) intent.putExtra("img",mainInt.getStringExtra("img"))
                startActivity(intent)
                finish()

            }
        }



        binding.diaryWriteEmojiXIv.setOnClickListener {
            finish()
        }




    }



}




