package com.linkmoa.source.domain.favorite.repository.adapter;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.linkmoa.source.domain.favorite.constant.ItemType;
import com.linkmoa.source.domain.favorite.entity.Favorite;
import com.linkmoa.source.domain.favorite.repository.FavoriteDataAccess;
import com.linkmoa.source.domain.favorite.repository.rdb.FavoriteJpaRepository;
import com.linkmoa.source.domain.favorite.repository.rdb.FavoriteQueryDslRepositoryImpl;
import com.linkmoa.source.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FavoriteDataAccessImpl implements FavoriteDataAccess {

	private final FavoriteJpaRepository favoriteJpaRepository;
	private final FavoriteQueryDslRepositoryImpl favoriteQueryDslRepository;

	@Override
	public Favorite findByItemIdAndItemType(Long itemId, ItemType itemType) {
		return favoriteJpaRepository.findByItemIdAndItemType(itemId, itemType);
	}

	@Override
	public List<Favorite> findByMember(Member member) {
		return favoriteJpaRepository.findByMember(member);
	}

	@Override
	public void incrementOrderIndexesForMember(Member member) {
		favoriteQueryDslRepository.incrementOrderIndexesForMember(member);
	}

	@Override
	public void decrementFavoriteOrderIndexes(Integer orderIndex) {
		favoriteQueryDslRepository.decrementFavoriteOrderIndexes(orderIndex);
	}

	@Override
	public void save(Favorite favorite) {
		favoriteJpaRepository.save(favorite);
	}

	@Override
	public void delete(Favorite favorite) {
		favoriteJpaRepository.delete(favorite);
	}
}
