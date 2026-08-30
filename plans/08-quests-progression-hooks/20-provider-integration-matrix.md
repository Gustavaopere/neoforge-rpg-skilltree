# 08.20 — Provider Integration Matrix for Narrative Events

## Goal
Integrar o conteúdo jogável do modpack à narrativa sem inventar mecânicas nem duplicar authority/pipelines.

## Contrato por adapter/provider
Cada integração deve registrar:
- mod/provider e versão exata;
- capability/event/query real;
- source authority;
- causal actor/owner;
- dedup identity;
- Narrative event/fact produzido;
- payload mínimo permitido;
- knowledge/evidence implications;
- fallback;
- fail-closed behavior;
- testes.

## Famílias prioritárias

### RPG Skill Tree
- level/XP/CPP/attributes;
- mastery/classes/specializations/perks via APIs canônicas;
- world scaling somente por boundaries existentes;
- nunca escrever attachment diretamente.

### Sobrevivência/TFC
- milestones discretos de metalurgia, agricultura, sobrevivência e tecnologia quando houver hooks reais;
- temperatura corporal continua Cold Sweat;
- sede/nutrição continuam providers instalados.

### Magia
- Ars Nouveau, Iron's, Goety, Malum, Eidolon, Hexalia, Vampirism e addons como providers independentes;
- classificação temática pode alimentar diálogo/ideologia;
- nunca unificar Source, mana, Soul Energy, spirits, sangue ou progressões nativas.

### Mobstein
- ressurreição corporal/experimentos/corpos/órgãos/allies somente por hooks reais da versão instalada;
- perks internas do Mobstein não viram nodes do RPG;
- necromancia temática não cria bridge automática.

### Volcanoes
- geological discovery, eruption, atmosphere, gases, respiration, pressure e hazards via APIs reais;
- nenhum Mastery/event spam por tick;
- não colapsar gases/pressão em magia.

### Enshrouded
- Shroud, Exposure, Flame, Sanctuary, Story State e ecology por boundaries canônicos;
- Shroud/Exposure != Black Arcana Corruption/Strain.

### Black Arcana
- cast pipeline e Arcane Danger por extension points reais;
- magia perigosa pode gerar Narrative facts/events, mas Narrative Core não reduz/aplica danger por conta própria.

### Create/AE2/Oritech/indústria
- milestones de comissionamento/descoberta somente com autoria causal;
- não gerar narrative XP por throughput/tick;
- factory/network presence deve usar hooks bounded, não scans globais.

### MineColonies
- tratado em adapter dedicado.

### Sable/Aeronautics/espaço
- first vehicle/airship/planet milestones somente por evento real e deduplicável;
- sublevel/vehicle state não vira encumbrance ou settlement automaticamente.

### Bosses/structures/dimensions
- discovery/defeat IDs persistentes;
- boss morto antes da quest é rota válida;
- estrutura encontrada cedo não deve desaparecer do histórico.

## Cobertura
Manter matriz provider → eventos narrativos e narrativa → provider. Mods de biblioteca, performance, tooltip, render ou compatibilidade sem gameplay não recebem arco artificial apenas para “ter lore”.

## Acceptance
Uma auditoria da modlist consegue classificar cada mod jogável como provider narrativo direto, contexto/solução, conteúdo sem hook, sistema universal ou não aplicável, sem inventar eventos inexistentes.