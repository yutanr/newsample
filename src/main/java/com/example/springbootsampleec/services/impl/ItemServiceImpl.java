package com.example.springbootsampleec.services.impl;
 
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
// gradle で追加
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.example.springbootsampleec.entities.Item;
import com.example.springbootsampleec.entities.User;
import com.example.springbootsampleec.repositories.ItemRepository;
import com.example.springbootsampleec.services.ItemService;

 
@Service
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;

	@Autowired
	private Environment environment; // 環境変数を使えるように。

	public ItemServiceImpl(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Item> findById(long id) {
		return itemRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Item> findAllByNameContaining(String name) {
		return itemRepository.findAllByNameContaining(name);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Item> findAllByDescriptionContaining(String description) {
		return itemRepository.findAllByDescriptionContaining(description);
	}

	@Transactional(readOnly = true)
	public List<Item> search(String name, String description) {
		List<Item> result = new ArrayList<Item>();
		// すべてブランクだった場合は全件検索する
		if ("".equals(name) && "".equals(description)) {
			result = itemRepository.findAll();
		} else if ("".equals(description)) {
			// descriptionが空欄の場合nameの値で検索する
			result = itemRepository.findAllByNameContaining(name);
		} else if ("".equals(name)) {
			// nameが空欄の場合descriptionの値で検索する
			result = itemRepository.findAllByDescriptionContaining(description);
		} else {
			List<Item> nameResult = itemRepository.findAllByNameContaining(name);
			List<Item> descriptionResult = itemRepository.findAllByDescriptionContaining(description);

			for (Item a : nameResult) {
				for (Item b : descriptionResult) {
					if (a.equals(b)) {
						result.add(a);
						break;
					}
				}
			}
		}
		return result;
	}

	@Transactional
	@Override
	public void updateItem(long id, String name, int price, int stock, String description) {
		Item item = findById(id).orElseThrow();
		item.setName(name);
		item.setPrice(price);
		item.setStock(stock);
		item.setDescription(description);
		itemRepository.saveAndFlush(item);
	}

	@Transactional
	@Override
	public void delete(long id) {
		Item item = findById(id).orElseThrow();
		itemRepository.delete(item);
	}

	@Override
	public void toggleLike(User user, long item_id) {
		Item item = findById(item_id).orElseThrow();
		if (item.getLikedUsers().contains(user)) {
			dislike(user, item);
			return;
		}
		like(user, item);
	}

	private void like(User user, Item item) {
		item.getLikedUsers().add(user);
		itemRepository.saveAndFlush(item);
	}

	private void dislike(User user, Item item) {
		item.getLikedUsers().remove(user);
		itemRepository.saveAndFlush(item);
	}

	@Transactional
	@Override
	public void register(String name, int price, int stock, String description, MultipartFile image) {
		if (image.getOriginalFilename().isEmpty()) {
			throw new RuntimeException("ファイルが設定されていません");
		}
		// 拡張子取得
		String extension = FilenameUtils.getExtension(image.getOriginalFilename());
		
		// ランダムなファイル名を設定
		String randomFileName = RandomStringUtils.randomAlphanumeric(20) + "." + extension;
		uploadImage(image, randomFileName);
		
		// Item エンティティの生成
		Item item = new Item(null, null, null, null, name, price, stock, description, randomFileName, null, null);

		// Item を保存
		itemRepository.saveAndFlush(item);
	}

	private void uploadImage(MultipartFile multipartFile, String fileName) {
		// 保存先のパスを作成
		Path filePath = Paths.get(environment.getProperty("sample.images.imagedir") + fileName);
		try {
			// ファイルをバイト列に変換して書き込み
			byte[] bytes = multipartFile.getBytes();
			OutputStream stream = Files.newOutputStream(filePath);
			stream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<Item> findAllById(long id) {
		// TODO 自動生成されたメソッド・スタブ
		return itemRepository.findAllById(id);
	}

	@Override
	public void getOrderItems(User user, long Item_id) {
		// TODO 自動生成されたメソッド・スタブ
		Item item = findById(Item_id).orElseThrow();
		addcart(user, item);
	}

	@Override
	public void getDeleteItems(User user, long Item_id) {
		// TODO 自動生成されたメソッド・スタブ
		Item item = findById(Item_id).orElseThrow();
		deletecart(user, item);

	}

	private void addcart(User user, Item item) {
		item.getOrderedUsers().add(user);
		itemRepository.saveAndFlush(item);
	}

	private void deletecart(User user, Item item) {
		item.getOrderedUsers().remove(user);
		itemRepository.saveAndFlush(item);
	}

}