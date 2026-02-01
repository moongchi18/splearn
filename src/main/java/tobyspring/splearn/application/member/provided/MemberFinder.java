package tobyspring.splearn.application.member.provided;

import tobyspring.splearn.domain.member.Member;

/*
    * 회원 조회 기능을 제공
* */
public interface MemberFinder {
    Member find(Long id);
}
