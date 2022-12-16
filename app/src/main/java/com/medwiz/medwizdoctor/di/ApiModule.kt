package com.medwiz.medwizdoctor.di
import com.medwiz.medwizdoctor.data.*
import com.medwiz.medwizdoctor.repository.auth.AuthRepoInterface
import com.medwiz.medwizdoctor.repository.auth.AuthRepository
import com.medwiz.medwizdoctor.repository.consulat.ConsultationRepoInterface
import com.medwiz.medwizdoctor.repository.consulat.ConsultationRepository
import com.medwiz.medwizdoctor.repository.doctor.DoctorRepoInterface
import com.medwiz.medwizdoctor.repository.doctor.DoctorRepository
import com.medwiz.medwizdoctor.repository.file.FileRepoInterface
import com.medwiz.medwizdoctor.repository.file.FileRepository
import com.medwiz.medwizdoctor.repository.prescription.PrescriptionRepoInterface
import com.medwiz.medwizdoctor.repository.prescription.PrescriptionRepository
import com.medwiz.medwizdoctor.repository.search.SearchRepoInterface
import com.medwiz.medwizdoctor.repository.search.SearchRepository
import com.medwiz.medwizdoctor.util.UtilConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
//OkHttpClient for filter logs
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(UtilConstants.baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    @Provides
    @Singleton
    fun provideAuth(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
    @Singleton
    @Provides
    fun provideAuthRepository( api: AuthApi) =
        AuthRepository(api) as AuthRepoInterface


    @Provides
    @Singleton
    fun provideDoctor(retrofit: Retrofit): DoctorApi {
        return retrofit.create(DoctorApi::class.java)
    }
    @Singleton
    @Provides
    fun provideDoctorRepository( api: DoctorApi) =
        DoctorRepository(api) as DoctorRepoInterface


    @Provides
    @Singleton
    fun providePrescription(retrofit: Retrofit): PrescriptionApi {
        return retrofit.create(PrescriptionApi::class.java)
    }
    @Singleton
    @Provides
    fun providePrescriptionRepository( api: PrescriptionApi) =
        PrescriptionRepository(api) as PrescriptionRepoInterface


    @Provides
    @Singleton
    fun provideFile(retrofit: Retrofit): FileApi {
        return retrofit.create(FileApi::class.java)
    }
    @Singleton
    @Provides
    fun provideFileRepository( api: FileApi) =
        FileRepository(api) as FileRepoInterface



    @Provides
    @Singleton
    fun provideConsultation(retrofit: Retrofit): ConsultationApi {
        return retrofit.create(ConsultationApi::class.java)
    }
    @Singleton
    @Provides
    fun provideConsultationRepository( api: ConsultationApi) =
        ConsultationRepository(api) as ConsultationRepoInterface

    @Provides
    @Singleton
    fun provideSearch(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }
    @Singleton
    @Provides
    fun provideSearchRepository( api: SearchApi) =
        SearchRepository(api) as SearchRepoInterface

}