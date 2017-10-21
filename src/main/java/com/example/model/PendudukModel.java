package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private Integer id;
	private String nik;
	private String nama;
	private String tempat_lahir;
	private String tanggal_lahir;
	private Integer jenis_kelamin;
	private Integer is_wni;
	private Integer id_keluarga;
	private String agama;
	private String pekerjaan;
	private String status_perkawinan;
	private String status_dalam_keluarga;
	private String golongan_darah;
	private Integer is_wafat;
	
	private KeluargaModel keluarga;
	
	public String convertJenisKelamin() {
		if (jenis_kelamin == 0) {
			return "Pria";
		} else {
			return "Wanita";
		}
	}
	
	public String convertWNIStatus() {
		if (is_wni == 0) {
			return "WNA";
		} else {
			return "WNI";
		}
	}
	
	public String convertWafatStatus() {
		if (is_wafat == 1) {
			return "Meninggal";
		} else {
			return "Hidup";
		}
	}
	
}