'use client';

import { useAptDetailQuery } from '@/hooks/api/useAptDetailQuery';
import stringToPrice from '@/hooks/stringToPrice';
import { useAptSidebarStore } from '@/zustand/useAptSidebarStore';
import { useCallback, useEffect, useRef } from 'react';
import AptChart from '../AptChart';
import { usePricePerPyeong } from '@/hooks/api/usePricePerPyeong';
import Link from 'next/link';

const PYEONG = 0.3025;

export default function AptDetailSidebar() {
  const { aptDetail, closeSidebar, isAptSidebarOpen } = useAptSidebarStore();
  const sideBarRef = useRef<HTMLDivElement>(null);

  const handleSidebarOutSideClick = useCallback(
    (e: any) => {
      if (sideBarRef.current && !sideBarRef.current.contains(e.target)) {
        closeSidebar();
      }
    },
    [closeSidebar],
  );

  const data = usePricePerPyeong(aptDetail?.apt_seq ?? '');
  const aptDeals = useAptDetailQuery(aptDetail?.apt_seq ?? '');

  useEffect(() => {
    window.addEventListener('mousedown', handleSidebarOutSideClick);

    return () => window.removeEventListener('mousedown', handleSidebarOutSideClick);
  }, [handleSidebarOutSideClick]);
  return (
    <>
      {isAptSidebarOpen && (
        <div ref={sideBarRef} className="absolute top-0 left-0 z-50">
          {aptDetail && (
            <div className="w-[400px] h-mapSize flex flex-col  overflow-y-auto bg-white">
              <Link
                target="_blank"
                href={`https://new.land.naver.com/complexes/115260?ms=${aptDetail.lat},${aptDetail.lng}`}
              >
                <h1 className="p-2 text-xl font-bold text-center hover:text-blue-500">
                  {aptDetail.apt_nm}
                </h1>
              </Link>
              <span className="self-end p-1 text-sm">건설연도:{aptDetail.build_year}</span>
              {aptDeals.length > 0 ? (
                <>
                  <div className="flex justify-between bg-slate-200 items-center">
                    <span className="p-2 text-lg font-semibold ">거래내역 ({aptDeals.length})</span>
                    <span className="text-xs text-end font-thin self-end">(2022년 이후)</span>
                  </div>
                  <div className="pt-6">
                    <AptChart data={data} />
                  </div>
                  <div>
                    {aptDeals.map(
                      ({
                        no,
                        deal_year,
                        deal_month,
                        deal_day,
                        deal_amount,
                        exclu_use_ar,
                        floor,
                      }) => (
                        <div key={no} className="border-b mb-2 p-2">
                          <div>
                            <span className="font-semibold">아파트</span> - {floor}층
                          </div>
                          <div>
                            <span className="font-semibold">거래날짜</span>: {deal_year}-
                            {String(deal_month).padStart(2, '0')}-
                            {String(deal_day).padStart(2, '0')}
                          </div>
                          <div>
                            <span className="font-semibold">전용면적</span>- {exclu_use_ar}㎡ /{' '}
                            {Math.round(exclu_use_ar * PYEONG)}평
                          </div>
                          <div>
                            <span className="font-semibold">매매 가격</span>:{' '}
                            {Math.floor(stringToPrice(deal_amount) / 100) / 100} 억원
                          </div>
                          <div>
                            <span className="font-semibold">3.3㎡당 가격</span>:{' '}
                            {Math.round(stringToPrice(deal_amount) / (exclu_use_ar * PYEONG))} 만원
                          </div>
                        </div>
                      ),
                    )}
                  </div>
                </>
              ) : (
                <div>최근 2년간 거래내역이 없습니다.</div>
              )}
            </div>
          )}
        </div>
      )}
    </>
  );
}
