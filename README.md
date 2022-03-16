
# 마음 속 작은 창문 “공간감”


> **공간감**은 익명의 사람과 나의 다이어리 한 페이지를 공유하고 서로 소통할 수 있는 1:1 다이어리 랜덤 공유 서비스입니다.
> 

![mainMockup](https://user-images.githubusercontent.com/89574881/158516877-bebf1e86-42fc-474e-abd2-0321773ee7db.jpeg)

# UI

![image](https://user-images.githubusercontent.com/89574881/158516906-ab30c7e0-ae49-485c-9e9f-dafe4360cd14.png)
![image](https://user-images.githubusercontent.com/89574881/158516902-ebe9b5dc-ca8a-4568-a4da-2f9c3c43a5c8.png)
![image](https://user-images.githubusercontent.com/89574881/158516919-8d8731ae-9659-43eb-8a1f-90af8c6513eb.png)

# Tech Stack

| Atchitecture | MVP |  |
| --- | --- | --- |
| Jetpack Components | Lifecycle, Data Binding |  |
| Network | Retrofit2 |  |
| Strategy |  |  |
| Other Tools | Notion, Slack |  |

# Foldering

```markdown
.
├── app
│   ├── build
│   │   ├── generated
│   │   │   ├── ap_generated_sources
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── data_binding_base_class_source_out
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   │           └── com
│   │   │   │               └── example
│   │   │   │                   └── gonggangam
│   │   │   │                       └── databinding
│   │   │   ├── res
│   │   │   │   ├── google-services
│   │   │   │   │   └── debug
│   │   │   │   │       └── values
│   │   │   │   ├── pngs
│   │   │   │   │   └── debug
│   │   │   │   │       └── drawable-anydpi-v24
│   │   │   │   └── resValues
│   │   │   │       └── debug
│   │   │   └── source
│   │   │       └── buildConfig
│   │   │           └── debug
│   │   │               └── com
│   │   │                   └── example
│   │   │                       └── gonggangam
│   │   ├── intermediates
│   │   │   ├── aar_metadata_check
│   │   │   │   └── debug
│   │   │   ├── annotation_processor_list
│   │   │   │   └── debug
│   │   │   ├── app_metadata
│   │   │   │   └── debug
│   │   │   ├── compatible_screen_manifest
│   │   │   │   └── debug
│   │   │   ├── compile_and_runtime_not_namespaced_r_class_jar
│   │   │   │   └── debug
│   │   │   ├── compressed_assets
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   │           └── assets
│   │   │   │               └── org
│   │   │   │                   └── threeten
│   │   │   │                       └── bp
│   │   │   ├── data_binding_base_class_log_artifact
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── data_binding_base_class_logs_dependency_artifacts
│   │   │   │   └── debug
│   │   │   ├── data_binding_dependency_artifacts
│   │   │   │   └── debug
│   │   │   ├── data_binding_layout_info_type_merge
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── desugar_graph
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   │           ├── currentProject
│   │   │   │           │   ├── dirs_bucket_0
│   │   │   │           │   ├── dirs_bucket_1
│   │   │   │           │   ├── dirs_bucket_2
│   │   │   │           │   ├── dirs_bucket_3
│   │   │   │           │   ├── jar_b3b88fe9c4923c1c191bbae27afe4fd7276d8663c44947614bb1e23ac6d83e24_bucket_0
│   │   │   │           │   ├── jar_b3b88fe9c4923c1c191bbae27afe4fd7276d8663c44947614bb1e23ac6d83e24_bucket_1
│   │   │   │           │   ├── jar_b3b88fe9c4923c1c191bbae27afe4fd7276d8663c44947614bb1e23ac6d83e24_bucket_2
│   │   │   │           │   └── jar_b3b88fe9c4923c1c191bbae27afe4fd7276d8663c44947614bb1e23ac6d83e24_bucket_3
│   │   │   │           ├── externalLibs
│   │   │   │           ├── mixedScopes
│   │   │   │           └── otherProjects
│   │   │   ├── desugar_lib_dex
│   │   │   │   └── debug
│   │   │   ├── dex
│   │   │   │   └── debug
│   │   │   │       ├── mergeExtDexDebug
│   │   │   │       ├── mergeLibDexDebug
│   │   │   │       │   ├── 0
│   │   │   │       │   ├── 1
│   │   │   │       │   ├── 10
│   │   │   │       │   ├── 11
│   │   │   │       │   ├── 12
│   │   │   │       │   ├── 13
│   │   │   │       │   ├── 14
│   │   │   │       │   ├── 15
│   │   │   │       │   ├── 2
│   │   │   │       │   ├── 3
│   │   │   │       │   ├── 4
│   │   │   │       │   ├── 5
│   │   │   │       │   ├── 6
│   │   │   │       │   ├── 7
│   │   │   │       │   ├── 8
│   │   │   │       │   └── 9
│   │   │   │       └── mergeProjectDexDebug
│   │   │   │           ├── 0
│   │   │   │           ├── 1
│   │   │   │           ├── 10
│   │   │   │           ├── 11
│   │   │   │           ├── 12
│   │   │   │           ├── 13
│   │   │   │           ├── 14
│   │   │   │           ├── 15
│   │   │   │           ├── 2
│   │   │   │           ├── 3
│   │   │   │           ├── 4
│   │   │   │           ├── 5
│   │   │   │           ├── 6
│   │   │   │           ├── 7
│   │   │   │           ├── 8
│   │   │   │           └── 9
│   │   │   ├── dex_archive_input_jar_hashes
│   │   │   │   └── debug
│   │   │   ├── dex_number_of_buckets_file
│   │   │   │   └── debug
│   │   │   ├── duplicate_classes_check
│   │   │   │   └── debug
│   │   │   ├── external_file_lib_dex_archives
│   │   │   │   └── debug
│   │   │   ├── external_libs_dex_archive
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── external_libs_dex_archive_with_artifact_transforms
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── incremental
│   │   │   │   ├── dataBindingGenBaseClassesDebug
│   │   │   │   ├── debug-mergeJavaRes
│   │   │   │   │   └── zip-cache
│   │   │   │   ├── mergeDebugAssets
│   │   │   │   ├── mergeDebugJniLibFolders
│   │   │   │   ├── mergeDebugResources
│   │   │   │   │   ├── merged.dir
│   │   │   │   │   └── stripped.dir
│   │   │   │   │       └── layout
│   │   │   │   ├── mergeDebugShaders
│   │   │   │   ├── packageDebug
│   │   │   │   │   └── tmp
│   │   │   │   │       └── debug
│   │   │   │   │           └── zip-cache
│   │   │   │   └── processDebugResources
│   │   │   ├── javac
│   │   │   │   └── debug
│   │   │   │       └── classes
│   │   │   │           └── com
│   │   │   │               └── example
│   │   │   │                   └── gonggangam
│   │   │   │                       └── databinding
│   │   │   ├── manifest_merge_blame_file
│   │   │   │   └── debug
│   │   │   ├── merged_assets
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   │           └── org
│   │   │   │               └── threeten
│   │   │   │                   └── bp
│   │   │   ├── merged_java_res
│   │   │   │   └── debug
│   │   │   ├── merged_jni_libs
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── merged_manifest
│   │   │   │   └── debug
│   │   │   ├── merged_manifests
│   │   │   │   └── debug
│   │   │   ├── merged_res
│   │   │   │   └── debug
│   │   │   ├── merged_res_blame_folder
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   │           ├── multi-v2
│   │   │   │           └── single
│   │   │   ├── merged_shaders
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── mixed_scope_dex_archive
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── navigation_json
│   │   │   │   └── debug
│   │   │   ├── packaged_manifests
│   │   │   │   └── debug
│   │   │   ├── processed_res
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── project_dex_archive
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   │           └── com
│   │   │   │               └── example
│   │   │   │                   └── gonggangam
│   │   │   │                       ├── Activity
│   │   │   │                       ├── Adapter
│   │   │   │                       ├── AuthService
│   │   │   │                       ├── Class
│   │   │   │                       ├── DiaryService
│   │   │   │                       ├── Fragment
│   │   │   │                       ├── Object
│   │   │   │                       ├── Util
│   │   │   │                       └── databinding
│   │   │   ├── runtime_symbol_list
│   │   │   │   └── debug
│   │   │   ├── signing_config_versions
│   │   │   │   └── debug
│   │   │   ├── stable_resource_ids_file
│   │   │   │   └── debug
│   │   │   ├── sub_project_dex_archive
│   │   │   │   └── debug
│   │   │   │       └── out
│   │   │   ├── symbol_list_with_package_name
│   │   │   │   └── debug
│   │   │   └── validate_signing_config
│   │   │       └── debug
│   │   ├── kotlin
│   │   │   └── compileDebugKotlin
│   │   │       └── caches-jvm
│   │   │           ├── inputs
│   │   │           ├── jvm
│   │   │           │   └── kotlin
│   │   │           └── lookups
│   │   ├── outputs
│   │   │   ├── apk
│   │   │   │   └── debug
│   │   │   └── logs
│   │   └── tmp
│   │       ├── compileDebugJavaWithJavac
│   │       └── kotlin-classes
│   │           └── debug
│   │               ├── META-INF
│   │               └── com
│   │                   └── example
│   │                       └── gonggangam
│   │                           ├── Activity
│   │                           ├── Adapter
│   │                           ├── AuthService
│   │                           ├── Class
│   │                           ├── DiaryService
│   │                           ├── Fragment
│   │                           └── Util
│   ├── libs
│   ├── sampledata
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── com
│       │           └── example
│       │               └── gonggangam
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── example
│       │   │           └── gonggangam
│       │   │               ├── Activity
│       │   │               ├── Adapter
│       │   │               ├── AuthService
│       │   │               ├── Class
│       │   │               ├── DiaryService
│       │   │               ├── Fragment
│       │   │               └── Util
│       │   └── res
│       │       ├── drawable
│       │       ├── drawable-v24
│       │       ├── font
│       │       ├── layout
│       │       ├── menu
│       │       ├── mipmap-anydpi-v26
│       │       ├── mipmap-hdpi
│       │       ├── mipmap-mdpi
│       │       ├── mipmap-xhdpi
│       │       ├── mipmap-xxhdpi
│       │       ├── mipmap-xxxhdpi
│       │       ├── values
│       │       └── values-night
│       └── test
│           └── java
│               └── com
│                   └── example
│                       └── gonggangam
├── build
│   └── kotlin
│       └── sessions
└── gradle
    └── wrapper
```
