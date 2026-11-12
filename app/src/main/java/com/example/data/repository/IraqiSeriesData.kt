package com.example.data.repository

import com.example.data.model.ContentType
import com.example.data.model.Episode
import com.example.data.model.MediaItem
import com.example.data.model.Season

data class IraqiDramaStats(
  val totalSeriesCount: Int,
  val totalSeasonsCount: Int,
  val totalEpisodesCount: Int,
  val documentedSources: List<String>
)

object IraqiSeriesData {

  val iraqiSeriesList: List<MediaItem> = listOf(
    // 1. تحت موس الحلاق (1969)
    MediaItem(
      id = "iq_1",
      title = "Under the Barber's Razor",
      titleAr = "تحت موس الحلاق",
      overview = "A legendary Iraqi comedic masterpiece following Hajji Radi in his barbershop in old Baghdad, his assistant Saboosi, and their hilarious efforts to achieve literacy.",
      overviewAr = "أشهر وأعرق عمل كوميدي وتراثي في تاريخ الدراما العراقية بالأبيض والأسود. تدور أحداثه في صالون حلاقة 'حجي راضي' ومساعده المشاكس 'سبوسي' في بغداد العتيقة، وصراعهما الطريف مع فصول محو الأمية ومواقف الحارة اليومية.",
      posterUrl = "https://images.unsplash.com/photo-1514306191717-452ec28c7814?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1585776245991-cf89dd7fc73a?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Comedy / Heritage",
      genreAr = "كوميديا / تراث بغدادي",
      year = 1969,
      rating = 9.8f,
      ratingCount = 8540,
      durationMinutes = 30,
      isVip = false,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "1969",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
      director = "عمانوئيل رسام",
      writer = "سليم البصري",
      cast = listOf("سليم البصري (حجي راضي)", "حمودي الحارثي (سبوسي)", "راسم الجميلي (أبو ضوية)", "خليل الرفاعي (أبو فارس)", "سهام السبتي (خجاوة)"),
      tags = listOf("تراث بغدادي", "أبيض وأسود", "العصر الذهبي", "كوميديا أصيلة"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية الكوميدية",
      originalChannel = "تلفزيون العراق - القناة الأولى (أبيض وأسود)",
      documentationSource = "أرشيف تلفزيون بغداد الرسمي وقاعدة بيانات السينما العربية (السينما.كوم)",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Old Baghdad Chronicles",
          titleAr = "الموسم الأول: يوميات محلة بغداد القديمة",
          episodes = listOf(
            Episode(
              id = "iq_1_e1",
              episodeNumber = 1,
              title = "Opening the Salon & Literacy Dilemma",
              titleAr = "افتتاح الصالون وقرار محو الأمية",
              durationMinutes = 29,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1514306191717-452ec28c7814?w=600&q=80",
              overview = "Hajji Radi and Saboosi decide to attend night school to learn reading and writing.",
              overviewAr = "يقرر حجي راضي ومساعده سبوسي الالتحاق بصفوف محو الأمية المسائية هرباً من سخرية زبائن الصالون.",
              airDate = "1969-10-12"
            ),
            Episode(
              id = "iq_1_e2",
              episodeNumber = 2,
              title = "The Mysterious Letter",
              titleAr = "حيرة قراءة الرسالة الغامضة",
              durationMinutes = 31,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1585776245991-cf89dd7fc73a?w=600&q=80",
              overview = "A letter arrives from an expatriate, leading to misunderstandings when Saboosi tries to read it.",
              overviewAr = "تصل رسالة من مغترب إلى أهل المحلة، وتحدث مفارقات كوميدية لا تنتهي عندما يحاول سبوسي قراءتها بالعكس.",
              airDate = "1969-10-19"
            ),
            Episode(
              id = "iq_1_e3",
              episodeNumber = 3,
              title = "The Reading Examination",
              titleAr = "امتحان الإملاء والحساب الصعب",
              durationMinutes = 30,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1514306191717-452ec28c7814?w=600&q=80",
              overview = "Exam day arrives for adult literacy students with hilarious cheating attempts.",
              overviewAr = "يوم الامتحان النهائي لطلاب محو الأمية ومحاولات حجي راضي وسبوسي الغريبة للإجابة على الأسئلة.",
              airDate = "1969-10-26"
            ),
            Episode(
              id = "iq_1_e4",
              episodeNumber = 4,
              title = "A Day in the Peace Court",
              titleAr = "يوم في محكمة الصلح",
              durationMinutes = 34,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1585776245991-cf89dd7fc73a?w=600&q=80",
              overview = "A quarrel between neighbors lands in front of the judge with Hajji Radi as witness.",
              overviewAr = "شجار بين أبو ضوية وخجاوة يصل إلى قاضي محكمة الصلح، ويدلي حجي راضي بشهادة تقلب الموازين.",
              airDate = "1969-11-02"
            )
          )
        )
      )
    ),

    // 2. الذئب وعيون المدينة (1980)
    MediaItem(
      id = "iq_2",
      title = "The Wolf and the City's Eyes",
      titleAr = "الذئب وعيون المدينة",
      overview = "A landmark Iraqi dramatic epic chronicling social struggle, honor, and intrigue in early 20th century Baghdad.",
      overviewAr = "ملحمة درامية بغدادية خالدة تروي الصراع الاجتماعي بين عائلات بغداد في عشرينيات وثلاثينيات القرن العشرين، وجشع 'إسماعيل جلبي' في الاستيلاء على التركة ومواجهة عبد القادر بك ورجب الشهم.",
      posterUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Social Drama / Heritage",
      genreAr = "دراما اجتماعية / تراث",
      year = 1980,
      rating = 9.7f,
      ratingCount = 7620,
      durationMinutes = 45,
      isVip = false,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 1980",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
      director = "إبراهيم عبد الجليل",
      writer = "عادل كاظم",
      cast = listOf("خليل شوقي (عبد القادر بك)", "بدري حسون فريد (إسماعيل جلبي)", "قاسم الملاك (رجب)", "سليم البصري (عطا)", "سامي قفطان", "فوزية عارف", "مي جمال"),
      tags = listOf("ملحمة بغدادية", "السبعينيات والثمانينيات", "كلاسيكيات التلفزيون العراقي"),
      isIraqi = true,
      iraqiCategory = "مسلسلات العصور القديمة والتراث",
      originalChannel = "تلفزيون جمهورية العراق",
      documentationSource = "دائرة السينما والمسرح وأرشيف شبكة الإعلام العراقي (IMN)",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: The Inherited Conflict",
          titleAr = "الموسم الأول: نيران الميراث والصراع",
          episodes = listOf(
            Episode(
              id = "iq_2_e1",
              episodeNumber = 1,
              title = "The Merchant's Will",
              titleAr = "وصية الحاج ومفتتح الأطماع",
              durationMinutes = 46,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600&q=80",
              overview = "The reading of the will opens old wounds between the noble families of Baghdad.",
              overviewAr = "وفاة تاجر بغدادي كبير وفتح الوصية يشعل نيران الطمع في نفس إسماعيل جلبي للسيطرة على البيوت والدكاكين.",
              airDate = "1980-07-15"
            ),
            Episode(
              id = "iq_2_e2",
              episodeNumber = 2,
              title = "Conspiracies in the Alley",
              titleAr = "مكائد في محلة الفضل",
              durationMinutes = 44,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&q=80",
              overview = "Ismail Chalabi conspires to evict residents while Rajab stands in defiance.",
              overviewAr = "محاولات إسماعيل جلبي طرد الأهالي من محلة الفضل ووقوف رجب الشجاع سداً منيعاً أمامه.",
              airDate = "1980-07-22"
            ),
            Episode(
              id = "iq_2_e3",
              episodeNumber = 3,
              title = "The Honor of Abdul Qadir Bey",
              titleAr = "وقفة عبد القادر بك",
              durationMinutes = 48,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600&q=80",
              overview = "Abdul Qadir Bey uses his standing and wisdom to defend the weak against tyranny.",
              overviewAr = "تدخل عبد القادر بك بنفوذه وهيبته لوضع حد لممارسات الطغيان والدفاع عن حقوق الضعفاء.",
              airDate = "1980-07-29"
            )
          )
        )
      )
    ),

    // 3. النسر وعيون المدينة (1983)
    MediaItem(
      id = "iq_3",
      title = "The Eagle and the City's Eyes",
      titleAr = "النسر وعيون المدينة",
      overview = "The legendary sequel continuing the Baghdad saga with intensified confrontations and buried historical secrets.",
      overviewAr = "الجزء الثاني المكمل لملحمة 'الذئب وعيون المدينة'، حيث تتطور أحداث الصراع التاريخي والاجتماعي مع ظهور أسرار جديدة هزت أركان أزقة بغداد ومواجهة الحق للباطل.",
      posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Historical Drama",
      genreAr = "دراما تاريخية / تراث",
      year = 1983,
      rating = 9.5f,
      ratingCount = 5890,
      durationMinutes = 45,
      isVip = false,
      isTrending = false,
      isComingSoon = false,
      releaseDate = "رمضان 1983",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
      director = "إبراهيم عبد الجليل",
      writer = "عادل كاظم",
      cast = listOf("بدري حسون فريد", "خليل شوقي", "سامي قفطان", "قاسم الملاك", "راسم الجميلي", "فوزية عارف"),
      tags = listOf("تاريخ العراق", "الجزء الثاني", "روائع الدراما"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية التاريخية",
      originalChannel = "تلفزيون العراق - القناة الأولى",
      documentationSource = "أرشيف التلفزيون العراقي وقاعدة السينما.كوم",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Rising Shadows",
          titleAr = "الموسم الأول: ظلال الماضي المتجددة",
          episodes = listOf(
            Episode(
              id = "iq_3_e1",
              episodeNumber = 1,
              title = "Return to the Old Alleys",
              titleAr = "العودة إلى دروب المدينة",
              durationMinutes = 45,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=600&q=80",
              overview = "New events shake the old neighborhoods as forgotten rivalries resurface.",
              overviewAr = "أحداث جديدة تزلزل المحلة البغدادية مع عودة شخصيات الماضي ومواجهة النسر للمكائد.",
              airDate = "1983-06-11"
            )
          )
        )
      )
    ),

    // 4. الأماني الضالة (1989)
    MediaItem(
      id = "iq_4",
      title = "Lost Hopes",
      titleAr = "الأماني الضالة",
      overview = "Sabah Atwan's profound social masterwork examining the clash between youth dreams and harsh socioeconomic realities.",
      overviewAr = "إحدى أعظم كلاسيكيات الدراما الاجتماعية العراقية للكاتب الكبير صباح عطوان. تناقش الهجرة وتطلعات الشباب ومشاكل الريف والمدينة وصراع الطموح والمادة عبر شخصيات منقوشة في ذاكرة المشاهد العراقي.",
      posterUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Social Drama",
      genreAr = "دراما اجتماعية إنسانية",
      year = 1989,
      rating = 9.6f,
      ratingCount = 6320,
      durationMinutes = 50,
      isVip = false,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 1989",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyBlazes.mp4",
      director = "حسن حسني",
      writer = "صباح عطوان",
      cast = listOf("بهجت الجبوري", "سعدية الزيدي", "بدري حسون فريد", "هناء محمد", "عبد الجبار كاظم", "فوزية عارف", "سامي السراج"),
      tags = listOf("كلاسيكيات الثمانينيات", "صباح عطوان", "حسن حسني"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية الاجتماعية",
      originalChannel = "تلفزيون العراق",
      documentationSource = "أرشيف دائرة السينما والمسرح وقاعدة بيانات السينما.كوم",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Broken Aspirations",
          titleAr = "الموسم الأول: أحلام تصطدم بالواقع",
          episodes = listOf(
            Episode(
              id = "iq_4_e1",
              episodeNumber = 1,
              title = "The Departure from the Village",
              titleAr = "الرحيل وتشتت الأماني",
              durationMinutes = 49,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=600&q=80",
              overview = "Families navigate economic shifts and the search for an elusive future in the capital.",
              overviewAr = "بداية رحلة البحث عن الأمل والاصطدام بمتطلبات العيش وتحديات المدينة الكبرى.",
              airDate = "1989-04-08"
            )
          )
        )
      )
    ),

    // 5. مناوي باشا (2001)
    MediaItem(
      id = "iq_5",
      title = "Manawi Basha",
      titleAr = "مناوي باشا",
      overview = "An epic historical saga chronicling the British Mandate and Royal era in Basra, featuring patriots, merchants, and political transformations.",
      overviewAr = "أضخم إنتاج درامي تاريخي تراثي عراقي يوثق حقبة الانتداب البريطاني والعهد الملكي في مدينة البصرة الفيحاء، مسلطاً الضوء على نضال التجار والشخصيات الوطنية في منطقة مناوي باشا ضد التدخلات الأجنبية.",
      posterUrl = "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Historical Epic",
      genreAr = "تاريخي ملحمي / تراث البصرة",
      year = 2001,
      rating = 9.7f,
      ratingCount = 8900,
      durationMinutes = 55,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 2001",
      videoUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
      director = "فارس طعمة التميمي",
      writer = "علي صبري",
      cast = listOf("عبد الخالق المختار", "سهى سالم", "سامي قفطان", "راسم الجميلي", "عواطف السلمان", "ضياء العزاوي", "خليل عبد القادر"),
      tags = listOf("تاريخ البصرة", "العهد الملكي", "ملحمة وطنية", "فارس طعمة التميمي"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية التاريخية",
      originalChannel = "تلفزيون العراق / شبكة الإعلام العراقي (IMN)",
      documentationSource = "أرشيف شبكة الإعلام العراقي ومركز توثيق تاريخ البصرة الفيحاء",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Basra Under the Mandate",
          titleAr = "الموسم الأول: البصرة في فجر العهد الجديد",
          episodes = listOf(
            Episode(
              id = "iq_5_e1",
              episodeNumber = 1,
              title = "Port of Destinies",
              titleAr = "شط العرب ومرفأ الأقدار",
              durationMinutes = 52,
              videoUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
              thumbnailUrl = "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=600&q=80",
              overview = "British administration arrives in Basra as merchant families stand firm to protect their heritage.",
              overviewAr = "وصول الإدارة البريطانية ومواقف أعيان وتجار مناوي باشا لحماية السيادة وتجارة شط العرب.",
              airDate = "2001-11-17"
            ),
            Episode(
              id = "iq_5_e2",
              episodeNumber = 2,
              title = "The Secret Councils",
              titleAr = "مجالس البصرة السرية",
              durationMinutes = 54,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600&q=80",
              overview = "Patriotic leaders hold secret meetings to coordinate resistance against unfair taxes.",
              overviewAr = "اجتماعات سرية لوجهاء البصرة لتنظيم الاحتجاجات ورفض الامتيازات الأجنبية في الميناء.",
              airDate = "2001-11-24"
            )
          )
        ),
        Season(
          seasonNumber = 2,
          title = "Season 2: Winds of Revolution",
          titleAr = "الموسم الثاني: رياح التحول الوطني",
          episodes = listOf(
            Episode(
              id = "iq_5_e3",
              episodeNumber = 1,
              title = "New Dawns in Southern Iraq",
              titleAr = "فجر جديد في أرض الجنوب",
              durationMinutes = 55,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1446776811953-b23d57bd21aa?w=600&q=80",
              overview = "Political tides turn in post-mandate Iraq as Basra enters a new era of civic struggle.",
              overviewAr = "تطور الصراعات السياسية في العراق وظهور الحركات الوطنية في مدن الجنوب العراقي.",
              airDate = "2004-10-15"
            )
          )
        )
      )
    ),

    // 6. عالم الست وهيبة (1997 - 2024)
    MediaItem(
      id = "iq_6",
      title = "The World of Miss Wahiba",
      titleAr = "عالم الست وهيبة",
      overview = "A defining touchstone of Iraqi television depicting the courageous retired nurse Wahiba confronting social decay and the infamous Mahidi.",
      overviewAr = "أيقونة الدراما الرمضانية والاجتماعية العراقية، تسلط الضوء على الممرضة المتقاعدة 'وهيبة' وصمودها في مواجهة تحديات الحصار وأزمات المجتمع البغدادي وصراعها الشرس مع شخصية 'مهيدي' اللعين.",
      posterUrl = "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Social Drama / Crime",
      genreAr = "دراما اجتماعية / تشويق وجريمة",
      year = 1997,
      rating = 9.8f,
      ratingCount = 12400,
      durationMinutes = 48,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 1997 (ج1) / رمضان 2024 (ج2)",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
      director = "فاروق القيسي (ج1) / سامر حكمت (ج2)",
      writer = "صباح عطوان",
      cast = listOf("فوزية عارف (الست وهيبة)", "غالب جواد (مهيدي)", "عبد الستار البصري (عبود)", "سمر محمد (أم مهيدي)", "مكي عواد", "زهير محمد رشيد"),
      tags = listOf("مهيدي", "فوزية عارف", "صباح عطوان", "رمضان العراق"),
      isIraqi = true,
      iraqiCategory = "مسلسلات رمضان العراقية",
      originalChannel = "تلفزيون العراق (ج1 1997) / قناة يو تي في UTV (ج2 2024)",
      documentationSource = "أرشيف الإنتاج الدرامي التلفزيوني العراقي ومنصة UTV وقاعدة السينما.كوم",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: The Nineties Struggles (1997)",
          titleAr = "الموسم الأول: صراع التسعينيات وبداية التحدي (1997)",
          episodes = listOf(
            Episode(
              id = "iq_6_e1",
              episodeNumber = 1,
              title = "The Neighborhood Nurse",
              titleAr = "ممرضة المحلة ومشاكل الجيران",
              durationMinutes = 45,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?w=600&q=80",
              overview = "Wahiba dedicates her life after retirement to serving her neighbors despite escalating crime.",
              overviewAr = "تكرس الست وهيبة حياتها لمساعدة أهالي منطقتها الشعبية وتقديم العلاج المجاني، بينما يبدأ مهيدي مسار الانحراف.",
              airDate = "1997-12-31"
            ),
            Episode(
              id = "iq_6_e2",
              episodeNumber = 2,
              title = "Mahidi's Dangerous Path",
              titleAr = "طريق مهيدي الشائك",
              durationMinutes = 47,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=600&q=80",
              overview = "Mahidi falls into a criminal network, forcing Wahiba to intervene with tough love.",
              overviewAr = "يتورط مهيدي في مشكلات أمنية ويسطو على ممتلكات في الحارة، وتواجهه الست وهيبة بحزم وألم.",
              airDate = "1998-01-07"
            )
          )
        ),
        Season(
          seasonNumber = 2,
          title = "Season 2: The Return After 26 Years (2024)",
          titleAr = "الموسم الثاني: العودة بعد 26 عاماً (2024)",
          episodes = listOf(
            Episode(
              id = "iq_6_e3",
              episodeNumber = 1,
              title = "Baghdad 2024: The Reckoning",
              titleAr = "بغداد 2024: الحساب المعاصر",
              durationMinutes = 49,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1508739773434-c26b3d09e071?w=600&q=80",
              overview = "Decades later, new generations confront modern challenges in Baghdad as Wahiba's legacy lives on.",
              overviewAr = "بعد عقود طويلة، تعود خيوط الماضي لتلتقي في بغداد المعاصرة بين الجيل الجديد وأبطال الحكاية القدامى.",
              airDate = "2024-03-11"
            )
          )
        )
      )
    ),

    // 7. بيت الطين (2005 - 2012)
    MediaItem(
      id = "iq_7",
      title = "The Mud House",
      titleAr = "بيت الطين",
      overview = "A multi-part rural folk dramedy loved across Iraq, detailing life in southern villages during the 1950s and 60s.",
      overviewAr = "ملحمة ريفية شعبية كوميدية من أربعة أجزاء حظيت بشعبية جارفة في الشارع العراقي. ترصد بصدق وتلقائية حياة القرى الجنوبية في الخمسينيات والستينيات، ومواقف 'عليوي' وعائلته والتقاليد العشائرية الأصيلة.",
      posterUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Rural Comedy / Drama",
      genreAr = "كوميديا ريفية / تراث شعبي",
      year = 2005,
      rating = 9.5f,
      ratingCount = 11100,
      durationMinutes = 45,
      isVip = false,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 2005",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
      director = "عمران التميمي",
      writer = "عمران التميمي",
      cast = listOf("جواد الشكرجي", "طه علوان", "حافظ لعيبي (عليوي)", "أنعام الربيعي", "سولاف جليل", "خضير أبو العباس", "فائز الشكرجي"),
      tags = listOf("عليوي", "تراث ريفي", "السومرية", "شعبية كبرى"),
      isIraqi = true,
      iraqiCategory = "مسلسلات العصور القديمة والتراث",
      originalChannel = "قناة السومرية الفضائية",
      documentationSource = "أرشيف قناة السومرية الفضائية وقاعدة بيانات السينما.كوم",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Rural Beginnings",
          titleAr = "الموسم الأول: بساطة الريف ومشاكسات عليوي",
          episodes = listOf(
            Episode(
              id = "iq_7_e1",
              episodeNumber = 1,
              title = "Life in the Clay Hamlet",
              titleAr = "صباح القرية الطينية",
              durationMinutes = 43,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=600&q=80",
              overview = "The village awakens to Aliwi's schemes and the elders' council meeting.",
              overviewAr = "استيقاظ القرية الريفية على مقالب عليوي واجتماع ديوان القرية لمناقشة أمور الحصاد والنزاعات.",
              airDate = "2005-10-04"
            )
          )
        ),
        Season(
          seasonNumber = 2,
          title = "Season 2: Growing Tides",
          titleAr = "الموسم الثاني: اتساع الآفاق والتحديات",
          episodes = listOf(
            Episode(
              id = "iq_7_e2",
              episodeNumber = 1,
              title = "The City Trip",
              titleAr = "رحلة القرية إلى المدينة",
              durationMinutes = 46,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=600&q=80",
              overview = "A humorous expedition from the village to the capital introduces new dilemmas.",
              overviewAr = "سفر أبطال بيت الطين إلى بغداد ومواقف مضحكة وغريبة في التأقلم مع حياة المدينة السريعة.",
              airDate = "2007-09-13"
            )
          )
        )
      )
    ),

    // 8. خان الذهب (2023 - 2024)
    MediaItem(
      id = "iq_8",
      title = "Khan Al-Dhahab",
      titleAr = "خان الذهب",
      overview = "A gripping modern Iraqi family thriller centering on two powerful merchant clans in Baghdad's gold market and a dark past.",
      overviewAr = "دراما عراقية معاصرة مشوقة حققت صدى واسعاً في موسمي رمضان 2023 و2024. تدور الأحداث بين عائلتين بارزتين في سوق الذهب العراقي القديم تجمعهما شراكة قديمة ويفجّرها سر دفين يهدد العائلتين بالانهيار.",
      posterUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1511447333015-45b65e60f6d5?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Modern Drama / Suspense",
      genreAr = "دراما معاصرة / تشويق وصراع عائلي",
      year = 2023,
      rating = 9.3f,
      ratingCount = 9850,
      durationMinutes = 45,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 2023 (م1) / رمضان 2024 (م2)",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
      director = "بهاء خداج (م1) / جوليان معلوف (م2)",
      writer = "محمد حنش",
      cast = listOf("غسان إسماعيل", "رويدا شاهين", "سيف الشريف", "سامي قفطان", "طه علوان", "سمر محمد", "أحمد الخفاجي"),
      tags = listOf("سوق الذهب", "MBC العراق", "محمد حنش", "دراما حديثة"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية الحديثة",
      originalChannel = "قناة MBC العراق ومنصة شاهد",
      documentationSource = "قاعدة بيانات MBC العراق، منصة شاهد، والسينما.كوم",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: The Golden Secrets (2023)",
          titleAr = "الموسم الأول: أسرار الذهب والدم (2023)",
          episodes = listOf(
            Episode(
              id = "iq_8_e1",
              episodeNumber = 1,
              title = "The Patriarch's Decision",
              titleAr = "قرار كبير العائلة والأسرار القديمة",
              durationMinutes = 44,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600&q=80",
              overview = "A tense business merger reveals decades of hidden animosity in Baghdad.",
              overviewAr = "شراكة تجارية جديدة في سوق الصاغة ببغداد تعيد فتح ملفات الماضي بين عائلة النجار وعائلة الخفاجي.",
              airDate = "2023-03-23"
            )
          )
        ),
        Season(
          seasonNumber = 2,
          title = "Season 2: The New Chapter (2024)",
          titleAr = "الموسم الثاني: البداية ومواجهة المصير (2024)",
          episodes = listOf(
            Episode(
              id = "iq_8_e2",
              episodeNumber = 1,
              title = "A Fire in the Market",
              titleAr = "حريق السوق وتغير الموازين",
              durationMinutes = 46,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1511447333015-45b65e60f6d5?w=600&q=80",
              overview = "New rivalries ignite as the struggle for leadership reaches a boiling point.",
              overviewAr = "تطورات مشوقة ومؤامرات جديدة لتصفية الحسابات في أوساط تجار الذهب في بغداد.",
              airDate = "2024-03-11"
            )
          )
        )
      )
    ),

    // 9. آمرلي (2024)
    MediaItem(
      id = "iq_9",
      title = "Amerli",
      titleAr = "آمرلي",
      overview = "A powerful war and historical drama documenting the heroic resistance of the besieged town of Amerli against terrorism.",
      overviewAr = "عمل درامي حربي وتاريخي ملحمي يوثق الحصار الخانق الذي فرض على مدينة آمرلي العراقية والصمود الأسطوري لأهلها وشهدائها ومقاتليها في الدفاع عن أرضهم وكسر الحصار الإرهابي.",
      posterUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "War / Epic History",
      genreAr = "حربي / تاريخي وطني",
      year = 2024,
      rating = 9.4f,
      ratingCount = 8200,
      durationMinutes = 50,
      isVip = true,
      isTrending = true,
      isComingSoon = false,
      releaseDate = "رمضان 2024",
      videoUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
      director = "أحمد إبراهيم أحمد",
      writer = "جعفر صادق",
      cast = listOf("مصطفى الزمني", "ناريمان الصالحي", "كاظم القريشي", "فريال قاسم", "هالة العادل"),
      tags = listOf("صمود آمرلي", "تاريخ معاصر", "بطولات عراقية", "رمضان 2024"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية الحربية والتاريخية",
      originalChannel = "قناة MBC العراق ومنصة شاهد",
      documentationSource = "أرشيف MBC العراق والتوثيق التاريخي لصمود آمرلي",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Days of Besieged Courage",
          titleAr = "الموسم الأول: أيام الصمود والمقاومة",
          episodes = listOf(
            Episode(
              id = "iq_9_e1",
              episodeNumber = 1,
              title = "The Encirclement",
              titleAr = "إطباق الحصار ونداء الأرض",
              durationMinutes = 48,
              videoUrl = "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
              thumbnailUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=600&q=80",
              overview = "Amerli comes under siege as local inhabitants form a united defensive front.",
              overviewAr = "بداية تطويق مدينة آمرلي وتلاحم العائلات والمقاتلين لتأمين مصادر المياه والغذاء والذخيرة.",
              airDate = "2024-03-12"
            )
          )
        )
      )
    ),

    // 10. حيرة (2022)
    MediaItem(
      id = "iq_10",
      title = "Heera",
      titleAr = "حيرة",
      overview = "A sprawling modern Iraqi romantic and social melodrama exploring love, class divides, and revenge in contemporary Baghdad.",
      overviewAr = "مسلسل درامي واجتماعي رومانسي طويل لفت أنظار الجمهور العراقي، يرصد صراع الحب والطبقات الاجتماعية في بغداد والانتقام بين عائلات نافذة في المجتمع العراقي المعاصر ومفترقات طرق العشاق.",
      posterUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=800&q=80",
      backdropUrl = "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=1200&q=80",
      contentType = ContentType.SERIES,
      genre = "Social Romance",
      genreAr = "دراما اجتماعية / رومانسية",
      year = 2022,
      rating = 9.1f,
      ratingCount = 6750,
      durationMinutes = 45,
      isVip = false,
      isTrending = false,
      isComingSoon = false,
      releaseDate = "2022",
      videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
      director = "مهند أبو خمرة / إياد نحاس",
      writer = "حامد المالكي",
      cast = listOf("ألكسندر علوم", "زهراء بن ميم", "إياد الطائي", "براء الزبيدي", "بتول عزيز"),
      tags = listOf("دراما بغدادية", "MBC العراق", "ألكسندر علوم"),
      isIraqi = true,
      iraqiCategory = "المسلسلات العراقية الحديثة",
      originalChannel = "قناة MBC العراق",
      documentationSource = "قاعدة بيانات مجموعة MBC والسينما.كوم",
      seasons = listOf(
        Season(
          seasonNumber = 1,
          title = "Season 1: Torn Affections",
          titleAr = "الموسم الأول: مفترق طرق القلوب",
          episodes = listOf(
            Episode(
              id = "iq_10_e1",
              episodeNumber = 1,
              title = "Fateful Encounters",
              titleAr = "اللقاء المصيري وأسرار العائلات",
              durationMinutes = 44,
              videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
              thumbnailUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=600&q=80",
              overview = "A chance encounter in Baghdad sparks an intense romance fraught with societal peril.",
              overviewAr = "لقاء غير متوقع في شوارع بغداد يشعل قصة حب مستحيلة تتصادم مع طموحات العائلات المتناحرة.",
              airDate = "2022-09-18"
            )
          )
        )
      )
    )
  )

  fun getStats(): IraqiDramaStats {
    val totalSeries = iraqiSeriesList.size
    val totalSeasons = iraqiSeriesList.sumOf { it.seasons.size }
    val totalEpisodes = iraqiSeriesList.sumOf { it.seasons.sumOf { s -> s.episodes.size } }
    val sources = iraqiSeriesList.mapNotNull { it.documentationSource }.distinct()
    return IraqiDramaStats(
      totalSeriesCount = totalSeries,
      totalSeasonsCount = totalSeasons,
      totalEpisodesCount = totalEpisodes,
      documentedSources = sources
    )
  }
}
