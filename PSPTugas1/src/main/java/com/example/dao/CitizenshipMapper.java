package com.example.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.One;

import com.example.model.*;

@Mapper
public interface CitizenshipMapper {

	// Mapper Penduduk
	@Select("select * from penduduk where nik = #{nik}")
	@Results(value = {
			@Result(property = "keluarga", column = "id_keluarga", javaType = KeluargaModel.class, one = @One(select = "selectKeluargaById")),
			@Result(property = "id_keluarga", column = "id_keluarga") })
	PendudukModel selectPenduduk(@Param("nik") String nik);

	@Select("select * from penduduk where id_keluarga = #{idk}")
	@Results(value = {
			@Result(property = "keluarga", column = "id_keluarga", javaType = KeluargaModel.class, one = @One(select = "selectKeluargaById")) })
	ArrayList<PendudukModel> selectPendudukByIdKeluarga(@Param("idk") Integer idk);

	@Update("UPDATE penduduk " + "SET " + "nik= #{nik}," + "nama= #{nama}," + "tempat_lahir= #{tempat_lahir},"
			+ "tanggal_lahir= #{tanggal_lahir}," + "jenis_kelamin= #{jenis_kelamin}," + "is_wni= #{is_wni},"
			+ "id_keluarga= #{id_keluarga}," + "agama= #{agama}," + "pekerjaan= #{pekerjaan},"
			+ "status_perkawinan= #{status_perkawinan}," + "status_dalam_keluarga= #{status_dalam_keluarga},"
			+ "golongan_darah= #{golongan_darah}," + "is_wafat= #{is_wafat} " + "WHERE id = #{id}")
	void updatePenduduk(PendudukModel penduduk);

	@Insert("INSERT INTO penduduk(id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void insertPenduduk(PendudukModel penduduk);

	@Select("SELECT COUNT(*) as pendudukCount from penduduk where nik like #{partialNIK}")
	Integer selectPendudukByGenderIncrement(String partialNIK);

	@Select("select max(id) from penduduk")
	Integer selectLargestPendudukId();

	// Mapper Keluarga
	@Select("select * from keluarga where nomor_kk = #{nomor_kk}")
	@Results(value = {
			@Result(property = "kelurahan", column = "id_kelurahan", javaType = KelurahanModel.class, one = @One(select = "selectKelurahanById")) })
	KeluargaModel selectKeluarga(@Param("nomor_kk") String nomor_kk);

	@Select("select max(id) from keluarga")
	Integer selectLargestKeluargaId();

	@Select("select * from keluarga where id = #{id}")
	@Results(value = {
			@Result(property = "kelurahan", column = "id_kelurahan", javaType = KelurahanModel.class, one = @One(select = "selectKelurahanById")) })
	KeluargaModel selectKeluargaById(@Param("id") Integer id);

	@Select("select id, nomor_kk from keluarga where 1")
	ArrayList<KeluargaModel> selectAllKeluarga();

	@Insert("INSERT INTO keluarga(id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void insertKeluarga(KeluargaModel keluarga);

	@Select("SELECT COUNT(*) as keluargaCount from keluarga where nomor_kk like #{partialNKK}")
	Integer selectKeluargaByPartialNKK(String partialNKK);

	@Update("UPDATE keluarga SET id=#{id}, nomor_kk=#{nomor_kk}, alamat=#{alamat}, rt=#{rt}, rw=#{rw}, id_kelurahan=#{id_kelurahan}, is_tidak_berlaku=#{is_tidak_berlaku} WHERE nomor_kk=#{nomor_kk}")
	void updateKeluargaByNKK(KeluargaModel keluarga);

	// Mapper Kelurahan
	@Select("select * from kelurahan where 1")
	ArrayList<KelurahanModel> selectAllKelurahan();

	@Select("select * from kelurahan where kode_kelurahan = #{kode_kelurahan}")
	@Results(value = {
			@Result(property = "kecamatan", column = "id_kecamatan", javaType = KecamatanModel.class, one = @One(select = "selectKecamatanById")) })
	KelurahanModel selectKelurahan(@Param("kode_kelurahan") String kode_kelurahan);

	@Select("select * from kelurahan where id_kecamatan = #{id_kecamatan}")
	ArrayList<KelurahanModel> selectAllKelurahanByIdKecamatan(@Param("id_kecamatan") Integer id_kecamatan);

	@Select("select * from kelurahan where id = #{id}")
	@Results(value = {
			@Result(property = "kecamatan", column = "id_kecamatan", javaType = KecamatanModel.class, one = @One(select = "selectKecamatanById")) })
	KelurahanModel selectKelurahanById(@Param("id") Integer id);

	// Mapper Kecamatan
	@Select("select * from kecamatan where 1")
	ArrayList<KecamatanModel> selectAllKecamatan();

	@Select("select * from kecamatan where kode_kecamatan = #{kode_kecamatan}")
	@Results(value = {
			@Result(property = "kota", column = "id_kota", javaType = KotaModel.class, one = @One(select = "selectKotaById")) })
	KecamatanModel selectKecamatan(@Param("kode_kecamatan") String kode_kecamatan);

	@Select("select * from kecamatan where id_kota = #{id_kota}")
	ArrayList<KecamatanModel> selectAllKecamatanByIdKota(@Param("id_kota") Integer id_kota);

	@Select("select * from kecamatan where id = #{id}")
	@Results(value = {
			@Result(property = "kota", column = "id_kota", javaType = KotaModel.class, one = @One(select = "selectKotaById")) })
	KecamatanModel selectKecamatanById(@Param("id") Integer id);

	// Mapper Kota
	@Select("select * from kota where 1")
	ArrayList<KotaModel> selectAllKota();

	@Select("select * from kota where kode_kota = #{kode_kota}")
	KotaModel selectKota(@Param("kode_kota") String kode_kota);

	@Select("select * from kota where id = #{id}")
	KotaModel selectKotaById(@Param("id") Integer id);
}
