package com.example.service;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface CitizenshipInterface {
	PendudukModel selectPenduduk(String nik);
	KeluargaModel selectKeluarga(String id_keluarga);
	KelurahanModel selectKelurahan(String kode_kelurahan);
	KecamatanModel selectKecamatan(String kode_kecamatan);
	KotaModel selectKota(String kode_kota);
	KeluargaModel selectKeluargaById(Integer id);
	KelurahanModel selectKelurahanById(Integer id);
	KecamatanModel selectKecamatanById(Integer id);
	KotaModel selectKotaById(Integer id);
}
