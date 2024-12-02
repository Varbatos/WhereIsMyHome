import { Area } from '@/types/MapType';
import React, { useCallback, useMemo } from 'react';
import { Polygon } from 'react-kakao-maps-sdk';
import { ClickArea } from './KaKaoMap';
import { useMapStore } from '@/zustand/useMapStore';
import useDebounce from '@/lib/useDebounce';

interface RegAreaProps {
  areas: Area[];
  clickedArea?: ClickArea | null;
  handleClickedArea: (area: ClickArea) => void;
}

export default function RegArea({ areas, handleClickedArea, clickedArea }: RegAreaProps) {
  const { isDistanceClick } = useMapStore();

  const handleAreaMouseOver = useDebounce((e: kakao.maps.Polygon) => {
    if (isDistanceClick) return;

    e.setOptions({ fillColor: '#989cff' });
  }, 0);

  const handleAreaMouseOut = useDebounce((e: kakao.maps.Polygon) => {
    if (isDistanceClick) return;

    e.setOptions({ fillColor: '#00ff0000' });
  }, 0);

  return (
    <>
      {areas.map((area: Area, index) => (
        <Polygon
          key={`area-${area.key}~${index}`}
          path={area.path}
          strokeWeight={1}
          strokeColor={'#00ff0000'}
          strokeOpacity={0.7}
          fillColor={`${clickedArea?.location === area.location ? '#85acff' : '#00ff0000'}`}
          fillOpacity={0.1}
          onMouseover={handleAreaMouseOver}
          onMouseout={handleAreaMouseOut}
          onClick={() => {
            if (isDistanceClick) return;

            handleClickedArea(area);
          }}
        ></Polygon>
      ))}
    </>
  );
}
