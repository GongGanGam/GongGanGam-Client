package com.example.gonggangam.Activity


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gonggangam.databinding.ActivityDiaryWriteEmojiBinding
import java.io.ByteArrayOutputStream


class DiaryWriteEmojiActivity : AppCompatActivity() {
    lateinit var binding: ActivityDiaryWriteEmojiBinding

    //    private lateinit var emojiDatas: ArrayList<Emoji>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDiaryWriteEmojiBinding.inflate(layoutInflater)


        setContentView(binding.root)

//        emojiDatas =ArrayList()
//        emojiDatas.apply{
//            add(Emoji(R.drawable.emoji_happy, "완전 행복해요"))
//            add(Emoji(R.drawable.emoji_fun, "매우 즐거워요"))
//            add(Emoji(R.drawable.emoji_soso, "그냥 그래요"))
//            add(Emoji(R.drawable.emoji_boring, "지루해요"))
//            add(Emoji(R.drawable.emoji_sad, "너무 슬퍼요"))
//            add(Emoji(R.drawable.emoji_upset, "매우 화나요"))
//            add(Emoji(R.drawable.emoji_annoyed, "진짜 짜증나요"))
//            add(Emoji(R.drawable.emoji_depressed, "많이 우울해요"))
//            add(Emoji(R.drawable.emoji_embarrassing, "창피해요"))
//            add(Emoji(R.drawable.emoji_excited, "설레요"))
//            add(Emoji(R.drawable.emoji_complicated, "복잡해요"))
//            add(Emoji(R.drawable.emoji_wonder, "궁금해요"))
//        }

        binding.diaryWriteEmojiEmojiIv.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

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

                intent.putExtra("state", "행복해요")
                intent.putExtra("image", byteArray)

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji02Iv.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji03Iv.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji04Iv.setOnClickListener {
            binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
            binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmojiIv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmojiIv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji02Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji02Iv.visibility = View.VISIBLE

            binding.diaryWriteEmojiEmoji03Iv.visibility = View.GONE
            binding.diaryWriteEmojiDarkEmoji03Iv.visibility = View.VISIBLE

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
                intent.putExtra("year",intent.getIntExtra("year",0))
                intent.putExtra("month",intent.getIntExtra("month",0))
                intent.putExtra("day",intent.getIntExtra("day",0))
                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji05Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji06Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji07Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji08Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji09Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji10Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji11Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }

        binding.diaryWriteEmojiEmoji12Iv.setOnClickListener {
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

                startActivity(intent);
                finish();
            }
        }


//        val emojiAdapter = DiaryWriteEmojiRVAdapter(emojiDatas)
//        binding.diaryWriteEmojiRecyclerView.adapter = emojiAdapter
//
//        binding.diaryWriteEmojiRecyclerView.layoutManager = GridLayoutManager(applicationContext, 3)
//        binding.diaryWriteEmojiRecyclerView.setHasFixedSize(true)
        binding.diaryWriteEmojiXIv.setOnClickListener {
            finish()
        }

//        emojiAdapter.setMyItemClickListener(object : DiaryWriteEmojiRVAdapter.MyItemClickListener{
//
//            override fun onItemClick(emoji: Emoji) {
//                changeEmoji(emoji)
//            }
//        })


    }


}

//    private fun changeEmoji(emoji:Emoji) {
//        binding.diaryWriteEmojiUnselectBtn.visibility = View.GONE
//        binding.diaryWriteEmojiSelectedBtn.visibility = View.VISIBLE
//
//    }


