'use client';

import Link from 'next/link';
import { useModalStore } from '../zustand/showModal';
import { Button } from './ui/button';
import { ChangeEvent, useState } from 'react';
import { Input } from './ui/input';
import useDebounce from '@/lib/useDebounce';
import { useKeywordStore } from '@/zustand/useKeywordStore';

export default function Header() {
  const [openSearch, setOpenSearch] = useState(false);
  const { setKeyword, openKeyword } = useKeywordStore();
  const { openModal, setVersion } = useModalStore();
  const toggleSearch = () => setOpenSearch((prev) => !prev);
  const handleKeywordInput = useDebounce((e: ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();

    openKeyword();
    setKeyword(e.target.value);
  }, 200);

  // const handleOnFocus = () => {
  //   console.log('포커스');
  //   openKeyword();
  // };

  return (
    <header className="flex justify-between items-center w-full h-[5vh] p-2 bg-blue-300">
      <Link className="" href="#" target="_blank">
        아파트 거래 검색 사이트
      </Link>
      <ul className="flex gap-2">
        <li className="flex items-center rounded-full shadow-md bg-white transition-all ease-out duration-300">
          <Button
            onClick={toggleSearch}
            variant={'ghost'}
            className={`relative w-8 h-8 mx-1 flex justify-center items-center rounded-full cursor-pointer transition-all ease-out duration-300 ${
              openSearch ? 'rotate-0' : 'rotate-90'
            }`}
          >
            <div
              className={`absolute flex justify-center items-center inset-0 border-2 rounded-full transition-all ease-out duration-300 ${
                openSearch ? 'border-blue-500' : 'border-gray-500'
              }`}
            >
              🍳
            </div>
          </Button>
          <div
            className={`relative transition-all ease-out duration-300 ${
              openSearch ? 'w-60 opacity-100' : 'w-0 opacity-0'
            } overflow-hidden relative`}
          >
            <Input
              type="text"
              placeholder="아파트명을 입력하세요"
              onChange={handleKeywordInput}
              // onFocus={handleOnFocus}
              className="w-full p-2 border-none rounded-none"
              style={{ visibility: openSearch ? 'visible' : 'hidden' }}
            />
          </div>
        </li>
        <li>
          <Button
            variant={'outline'}
            onClick={() => {
              setVersion('login');
              openModal();
            }}
          >
            로그인
          </Button>
        </li>
      </ul>
    </header>
  );
}
