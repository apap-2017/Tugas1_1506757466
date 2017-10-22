package com.example.model;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private Integer id;
	private String nomor_kk;
	private String alamat;
	private String rt;
	private String rw;
	private Integer id_kelurahan;
	private Integer is_tidak_berlaku;
	
	private KelurahanModel kelurahan;
	private ArrayList<PendudukModel> pendudukList;
}
