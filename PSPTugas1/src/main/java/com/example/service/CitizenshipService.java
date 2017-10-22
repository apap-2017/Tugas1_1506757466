package com.example.service;

import com.example.dao.CitizenshipMapper;
import com.example.model.*;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CitizenshipService implements CitizenshipInterface {
	@Autowired
	private CitizenshipMapper citizenshipMapper;

	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("select penduduk with nik {}", nik);
		return citizenshipMapper.selectPenduduk(nik);
	}
	
	public ArrayList<PendudukModel> selectPendudukByIdKeluarga(Integer idk) {
		log.info("select penduduk with idk {}", idk);
		return citizenshipMapper.selectPendudukByIdKeluarga(idk);
	}
	
	public ArrayList<PendudukModel> searchPendudukByLocation(Integer idkel) {
		log.info("select penduduk with idk {}", idkel);
		return citizenshipMapper.searchPendudukByLocation(idkel);
	}
	
	public Integer selectPendudukByGenderIncrement(String partialNIK) {
		log.info("select penduduk with nik part {}", partialNIK);
		return citizenshipMapper.selectPendudukByGenderIncrement(partialNIK);
	}
	
	public Integer selectLargestPendudukId() {
		log.info("select penduduk with biggest ID");
		return citizenshipMapper.selectLargestPendudukId();
	}
	
	public void updatePenduduk(PendudukModel pm) {
		log.info("update penduduk with id {}", pm.getId());
		citizenshipMapper.updatePenduduk(pm);
	}
	
	public void insertPenduduk(PendudukModel pm) {
		log.info("penduduk inserted with id {}", pm.getId());
		citizenshipMapper.insertPenduduk(pm);
	}
	
	public void insertKeluarga(KeluargaModel km) {
		log.info("keluarga inserted with id {}", km.getId());
		citizenshipMapper.insertKeluarga(km);
	}
	
	public void updateKeluargaByNKK(KeluargaModel km) {
		log.info("update keluarga with id {}", km.getId());
		citizenshipMapper.updateKeluargaByNKK(km);
	}
	
	public Integer selectKeluargaByPartialNKK(String partialNKK) {
		log.info("select keluarga with nkk part {}", partialNKK);
		return citizenshipMapper.selectKeluargaByPartialNKK(partialNKK);
	}
	
	public Integer selectLargestKeluargaId() {
		log.info("select keluarga with biggest ID");
		return citizenshipMapper.selectLargestKeluargaId();
	}

	public KeluargaModel selectKeluarga(String nomor_kk) {
		log.info("select keluarga with nkk {}", nomor_kk);
		return citizenshipMapper.selectKeluarga(nomor_kk);
	}
	
	public ArrayList<KeluargaModel> selectAllKeluarga() {
		log.info("select all keluarga");
		return citizenshipMapper.selectAllKeluarga();
	}
	
	public ArrayList<KelurahanModel> selectAllKelurahan() {
		log.info("select all kelurahan");
		return citizenshipMapper.selectAllKelurahan();
	}
	
	public ArrayList<KelurahanModel> selectAllKelurahanByIdKecamatan(Integer id_kecamatan) {
		log.info("select all kelurahan");
		return citizenshipMapper.selectAllKelurahanByIdKecamatan(id_kecamatan);
	}
	
	public ArrayList<KecamatanModel> selectAllKecamatan() {
		log.info("select all kecamatan");
		return citizenshipMapper.selectAllKecamatan();
	}
	
	public ArrayList<KecamatanModel> selectAllKecamatanByIdKota(Integer id_kota) {
		log.info("select all kecamatan");
		return citizenshipMapper.selectAllKecamatanByIdKota(id_kota);
	}
	
	public ArrayList<KotaModel> selectAllKota() {
		log.info("select all kota");
		return citizenshipMapper.selectAllKota();
	}

	public KelurahanModel selectKelurahan(String kode_kelurahan) {
		log.info("select kelurahan with kkel {}", kode_kelurahan);
		return citizenshipMapper.selectKelurahan(kode_kelurahan);
	}

	public KecamatanModel selectKecamatan(String kode_kecamatan) {
		log.info("select kecamatan with kkec {}", kode_kecamatan);
		return citizenshipMapper.selectKecamatan(kode_kecamatan);
	}

	public KotaModel selectKota(String kode_kota) {
		log.info("select kota with kkot {}", kode_kota);
		return citizenshipMapper.selectKota(kode_kota);
	}

	@Override
	public KeluargaModel selectKeluargaById(Integer id) {
		log.info("select keluarga with id {}", id);
		return citizenshipMapper.selectKeluargaById(id);
	}

	@Override
	public KelurahanModel selectKelurahanById(Integer id) {
		log.info("select kelurahan with id {}", id);
		return citizenshipMapper.selectKelurahanById(id);
	}

	@Override
	public KecamatanModel selectKecamatanById(Integer id) {
		log.info("select kecamatan with id {}", id);
		return citizenshipMapper.selectKecamatanById(id);
	}

	@Override
	public KotaModel selectKotaById(Integer id) {
		log.info("select kota with id {}", id);
		return citizenshipMapper.selectKotaById(id);
	}
}
