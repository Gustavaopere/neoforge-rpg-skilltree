# Simply Swords x Excalibur

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db8161b94df52a2f4ada39
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Simply Swords x Excalibur.zip`
- **Versão 1.21.1:** —
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra captura física da pasta Resource Packs do perfil em 08/09/2026 como evidência de instalação de `Simply Swords x Excalibur.zip`.
- O projeto não publica versão semântica própria e usa filename sem número conclusivo; `Versão 1.21.1` permanece vazia.
- O alvo físico registrado é Simply Swords `1.70.2-1.21.1`; Simply More não é presumido como coberto.

## Propriedades do banco

- **Mod:** Simply Swords x Excalibur
- **Arquivo JAR:** `Simply Swords x Excalibur.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** —
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Visual, Compat, RPG
- **Função:** Compatibility resource pack não oficial que integra as texturas das armas do Simply Swords à estética Excalibur, sem alterar stats, abilities ou combat logic.
- **Dependências:** Excalibur base + Simply Swords 1.70.2-1.21.1. Não cobre Simply More automaticamente; esse addon possui resource pack separado na lista especial.
- **Sobreposição:** Compete com outros retextures/models de Simply Swords por prioridade. Epic Fight/Better Combat podem alterar apresentação/transform em mão, mas incompatibilidade funcional não é presumida sem evidência.
- **Compatibilidade/Riscos:** Sem versão semântica publicada. O mesmo filename é distribuído para várias versões; compatibilidade com a build física do Simply Swords exige QA de cada model/texture. Pode colidir com outros Simply Swords retextures.
- **Observações:** Arquivo instalado `Simply Swords x Excalibur.zip`; projeto não publica versão semântica própria, portanto `Versão 1.21.1` permanece vazia. Simply More não é presumido como coberto.
- **Procedência:** CurseForge oficial Simply Swords x Excalibur + captura Resource Packs do perfil em 08/09/2026 + modlist física Simply Swords 1.70.2-1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/simply-swords-x-excalibur
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — sem versão semântica, Simply Swords 1.70.2-1.21.1, escopo visual, boundary Simply More, overlaps, riscos e QA catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Simply Swords x Excalibur.zip`, sem versão semântica publicada. O alvo físico atual é Simply Swords `1.70.2-1.21.1`.

## 1. Papel e authority
Simply Swords x Excalibur é um compatibility pack visual não oficial que adapta texturas de Simply Swords ao estilo Excalibur. Simply Swords continua authority de weapon registry, stats, unique effects/abilities e qualquer integração de combate.

## 2. Cobertura confirmada
O upstream define o projeto como integração das **textures do Simply Swords** ao Excalibur. Não há base segura para transformar isso em cobertura integral de todo model/item sem inventário do ZIP.

## 3. Versão e distribuição
O projeto usa o mesmo filename `Simply Swords x Excalibur.zip` e não publica versão semântica própria. O campo de versão permanece vazio em vez de inferir uma versão pela data ou pela versão do jogo.

## 4. Boundary com Simply More
`Simply More` é addon separado e possui seu próprio compatibility pack na lista do usuário. Este dossier não atribui cobertura do Simply More ao Simply Swords x Excalibur.

## 5. Load order e animações
O pack deve prevalecer sobre Excalibur/base assets relevantes. Sistemas como Epic Fight ou Better Combat podem mudar item transforms/animações em mão; isso cria superfície de QA visual, não incompatibilidade funcional presumida.

## 6. Client e reload
Resource reload deve alterar somente textures/models de item. Damage, attributes, abilities, cooldowns e weapon data permanecem no Simply Swords.

## 7. Riscos
1. Simply Swords 1.70.2 conter item/path posterior ao resource pack.
2. Outro Simply Swords retexture sobrescrever os mesmos assets.
3. Weapon transform ficar visualmente incoerente com animações de combate.
4. Simply More ser confundido como coberto.
5. Resource reload manter item model stale.

## 8. Matriz de testes
- [ ] Amostrar weapon classes principais do Simply Swords.
- [ ] Conferir unique weapons e icons.
- [ ] Testar primeira/terceira pessoa com combat animations ativas.
- [ ] Confirmar que Simply More usa seu próprio pack quando necessário.
- [ ] Resource reload sem missing model/texture.
- [ ] Confirmar que pack on/off não altera stats/abilities.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma o propósito de integração visual com Excalibur e o filename sem versão semântica. O catálogo limita a autoridade ao visual.

> Boundary canônico: **Simply Swords controla armas e gameplay; este pack controla apenas as texturas/models que fornece**.
